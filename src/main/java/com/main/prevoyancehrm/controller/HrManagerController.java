package com.main.prevoyancehrm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.dto.RequestDto.AddEmpLeave;
import com.main.prevoyancehrm.dto.RequestDto.HolidayRequest;
import com.main.prevoyancehrm.dto.RequestDto.OnboardingRequest;
import com.main.prevoyancehrm.dto.responseObjects.DataResponse;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.entities.BalanceLeaves;
import com.main.prevoyancehrm.entities.Holidays;
import com.main.prevoyancehrm.entities.ProfessionalDetail;
import com.main.prevoyancehrm.entities.Salary;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.exceptions.UnauthorizeException;
import com.main.prevoyancehrm.helper.ExcelFormater;
import com.main.prevoyancehrm.service.serviceImpl.*;
import com.main.prevoyancehrm.service.serviceLogic.EmployeeDefaultAssignElements;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/hrManager")
@CrossOrigin
public class HrManagerController {

    private final BankDetailServiceImpl bankDetailServiceImpl;

    private final UserServiceImpl userServiceImpl;
    private final EmailServiceImpl emailServiceImpl;
    private final SalaryServiceImpl salaryServiceImpl;
    private final ExcelFormater excelFormater;
    private final EmployeeDefaultAssignElements assignElements;
    private final ProfessionalDetailServiceImpl professionalDetailServiceImpl;
    private final HolidaysServiceImpl holidaysServiceImpl;
    private final BalanceLeaveServiceImpl balanceLeaveServiceImpl;

    public HrManagerController(
            UserServiceImpl userServiceImpl,
            EmailServiceImpl emailServiceImpl,
            SalaryServiceImpl salaryServiceImpl,
            ExcelFormater excelFormater,
            EmployeeDefaultAssignElements assignElements,
            ProfessionalDetailServiceImpl professionalDetailServiceImpl,
            HolidaysServiceImpl holidaysServiceImpl,
            BalanceLeaveServiceImpl balanceLeaveServiceImpl
    , BankDetailServiceImpl bankDetailServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.emailServiceImpl = emailServiceImpl;
        this.salaryServiceImpl = salaryServiceImpl;
        this.excelFormater = excelFormater;
        this.assignElements = assignElements;
        this.professionalDetailServiceImpl = professionalDetailServiceImpl;
        this.holidaysServiceImpl = holidaysServiceImpl;
        this.balanceLeaveServiceImpl = balanceLeaveServiceImpl;
        this.bankDetailServiceImpl = bankDetailServiceImpl;
    }


    @PostMapping("/onboardEmployee")
    public ResponseEntity<SuccessResponse> onboardEmployee(@RequestHeader("Authorization")String jwt,@Valid @RequestBody OnboardingRequest request) throws Exception{
        SuccessResponse response = new SuccessResponse();
        User userEmployee = this.userServiceImpl.getUserByJwt(jwt);
        ProfessionalDetail professionalDetail = new ProfessionalDetail();
        Salary salary = new Salary();

        if(request.getRole()==null){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("you need to pass role");
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        
        Role role = Role.valueOf(request.getRole().toUpperCase());
        if(userEmployee.getRole().equals(Role.HRMANAGER) &&  (role.equals(Role.ADMIN) || role.equals(Role.SUPERADMIN)||role.equals(Role.HRMANAGER))){
            throw new UnauthorizeException("you don't have credentials to change this role !");
        }
        if(userEmployee.getRole().equals(Role.ADMIN) &&  (role.equals(Role.ADMIN) || role.equals(Role.SUPERADMIN))){
            throw new UnauthorizeException("you don't have credentials to change this role !");
        }
        
        User user = new User();
        user = userServiceImpl.getUserByEmail(request.getEmail());
        System.out.println(user.getId());
        if(user==null || !user.getId().equals(request.getCandidateId())){
            throw new EntityNotFoundException("email or id Not match !");
        }
        user.setRole(role);
        String email = user.getEmail();
        String name = user.getFirstName();
        String position = user.getProfessionalDetail().getPosition();
        String mobileNo = user.getMobileNo();
        CompletableFuture.runAsync(()->emailServiceImpl.welcomeEmail(email,name,position,mobileNo));
        salary = user.getSalary();
        salary.setGrossSalary(request.getGrossSalary());

        professionalDetail = user.getProfessionalDetail();
        professionalDetail.setJoiningDate(request.getJoiningDate());
    
        this.professionalDetailServiceImpl.addProfessionalDetail(professionalDetail);
        this.salaryServiceImpl.addSalary(salary);
        user.setActive(true);
        user.setEmployee(true);
        user.setApproved(true);
        user.setEmployeeId(request.getEmployeeId());   
        User user2 = this.userServiceImpl.updateUser(user);

        CompletableFuture.runAsync(()->this.assignElements.assignAllLeaveTypes(user2.getId()));
        try{
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Employee Onboard Successfully!");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/getAllCandidates")
    public ResponseEntity<DataResponse> getAllCandidates(@RequestParam(required = false)String query,
                                                         @RequestParam(required = false)String department
                                                        ) throws Exception{
        DataResponse response = new DataResponse();
        try{
            response.setData(this.userServiceImpl.getAllCandidates(query, department));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Get All Candidate Successfully!");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
           throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<DataResponse> getAllEmployee(@RequestParam(required = false)String query,
                                                         @RequestParam(required = false)String department
                                                        ) throws Exception{
        DataResponse response = new DataResponse();
        try{
            response.setData(this.userServiceImpl.getAllEmployees(query, department));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Get All Employee Successfully!");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/exportEmployees")
    public ResponseEntity<?> exportEmployee(@RequestParam(required = false)String position,
                                            @RequestParam(required = false)String department
                                                        ) throws Exception{
        try{ 
            byte[] excelBytes = this.excelFormater.exportEmployee(this.userServiceImpl.exportEmployee(position, department));

            ByteArrayResource resource = new ByteArrayResource(excelBytes);

                    return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xlsx")
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .body(resource);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/importCandidates")
    public ResponseEntity<SuccessResponse> importCandidates(@RequestPart("file")MultipartFile file) throws Exception{
        SuccessResponse response = new SuccessResponse();
        try{
            this.excelFormater.importCandidatesFromExcel(file);
            response.setHttpStatus(HttpStatus.OK);
            response.setHttpStatusCode(200);
            response.setMessage("Candidates Added Successfully ! ");
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    
    @PostMapping("/importEmployees")
    public ResponseEntity<SuccessResponse> importEmployees(@RequestPart("file")MultipartFile file) throws Exception{
        SuccessResponse response = new SuccessResponse();
        List<String> errorEmails = new ArrayList<>();
        try{
            errorEmails= this.excelFormater.importEmployee(file);
            if(errorEmails.isEmpty()){
                response.setHttpStatus(HttpStatus.OK);
                response.setHttpStatusCode(200);
                response.setMessage("Employees Added Successfully ! ");
                return ResponseEntity.of(Optional.of(response));
            }else{
                response.setHttpStatus(HttpStatus.OK);
                response.setHttpStatusCode(200);
                response.setMessage(errorEmails.toString()+"this users information is not fomated , rest are added successfully");
                return ResponseEntity.of(Optional.of(response));
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/getEmptyCandidateSheet")
    public ResponseEntity<?> exportEmptyCandidateSheet() throws Exception{
        try{ 
            byte[] excelBytes = this.excelFormater.emptyCandidateSheet();

            ByteArrayResource resource = new ByteArrayResource(excelBytes);
                    return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xlsx")
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .body(resource);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/getEmptyEmployeeSheet")
    public ResponseEntity<?> exportEmptyEmployeeSheet() throws Exception{
        try{ 
            byte[] excelBytes = this.excelFormater.emptyEmployeeSheet();

            ByteArrayResource resource = new ByteArrayResource(excelBytes);

                    return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xlsx")
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .body(resource);
                    // return ResponseEntity.status(HttpStatus.OK).body("ok");
        }catch(Exception e){
            throw new Exception();
        }
    }

    @PostMapping("/addHoliday")
    public ResponseEntity<SuccessResponse> addHoliday(@RequestBody HolidayRequest request)throws Exception{
        try{
            Holidays holidays = new Holidays();
            holidays.setDate(request.getDate());
            holidays.setName(request.getName());
            holidays.setDescription(request.getDescription());
            this.holidaysServiceImpl.addHolidays(holidays);
            SuccessResponse response = new SuccessResponse(HttpStatus.OK,200,"holiday added successfully !");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("addEmployeeLeave/{employeeId}")
    public ResponseEntity<SuccessResponse> addEmployeeLeave(@PathVariable("employeeId")String employeeId,@RequestBody AddEmpLeave request) throws Exception{
        SuccessResponse response = new SuccessResponse();
        try{
            System.out.println("ok");
            System.out.println(employeeId + " "+request.getLeaveId()+" "+request.getLeavesTaken());
            BalanceLeaves balanceLeaves = this.balanceLeaveServiceImpl.getBalanceLeaveByIdAndEmpId(request.getLeaveId(), employeeId);
        
            if(balanceLeaves==null){
                throw new EntityNotFoundException("leave not found !");
            }
            balanceLeaves.setLeavesTaken(balanceLeaves.getLeavesTaken()+request.getLeavesTaken());
            balanceLeaves.setBalanceLeaves(balanceLeaves.getBalanceLeaves()-request.getLeavesTaken());
            this.balanceLeaveServiceImpl.addBalanceLeaves(balanceLeaves);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Leave Added Successfully!");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
        

}
