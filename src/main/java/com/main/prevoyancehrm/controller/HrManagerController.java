package com.main.prevoyancehrm.controller;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.dto.RequestDto.OnboardingRequest;
import com.main.prevoyancehrm.dto.responseObjects.DataResponse;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.entities.BalanceLeaves;
import com.main.prevoyancehrm.entities.Salary;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.helper.ExcelFormater;
import com.main.prevoyancehrm.service.serviceImpl.EmailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.SalaryServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/hrManager")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class HrManagerController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private SalaryServiceImpl salaryServiceImpl;

    @Autowired
    private ExcelFormater excelFormater;


    @PostMapping("/onboardEmployee")
    public ResponseEntity<SuccessResponse> onboardEmployee(@RequestHeader("Authorization")String jwt,@RequestBody OnboardingRequest request){
        SuccessResponse response = new SuccessResponse();
        User userEmployee = this.userServiceImpl.getUserByJwt(jwt);
        Salary salary = new Salary();

        if(request.getRole()==null){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("you need to pass role");
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        
        Role role = Role.valueOf(request.getRole().toUpperCase());
        if(userEmployee.getRole().equals(Role.HRMANAGER) &&  (role.equals(Role.ADMIN) || role.equals(Role.SUPERADMIN)||role.equals(Role.HRMANAGER))){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("you don't have credentials to change this role !");
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        if(userEmployee.getRole().equals(Role.ADMIN) &&  (role.equals(Role.ADMIN) || role.equals(Role.SUPERADMIN))){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("you don't have credentials to change this role !");
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        
        User user = new User();
        user = userServiceImpl.getUserByEmail(request.getEmail());
        if(user==null || user.getId()!=request.getId()){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("email or id Not match !");
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        user.setRole(role);
        String email = user.getEmail();
        String name = user.getFirstName();
        String position = user.getProfessionalDetail().getPosition();
        String mobileNo = user.getMobileNo();
        salary.setGrossSalary(request.getGrossSalary());
        salary.setUser(user);
        this.salaryServiceImpl.addSalary(salary);
        CompletableFuture.runAsync(()->emailServiceImpl.welcomeEmail(email,name,position,mobileNo));
        user.setActive(true);
        user.setEmployee(true);
        this.userServiceImpl.registerUser(user);
        BalanceLeaves balanceLeaves = new BalanceLeaves();
        balanceLeaves.setUser(user);
        try{
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Employee Onboard Successfully!");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getAllCandidates")
    public ResponseEntity<DataResponse> getAllCandidates(@RequestParam(required = false)String query,
                                                         @RequestParam(required = false)String department
                                                        ){
        DataResponse response = new DataResponse();
        try{
            response.setData(this.userServiceImpl.getAllCandidates(query, department));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Get All Candidate Successfully!");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<DataResponse> getAllEmployee(@RequestParam(required = false)String query,
                                                         @RequestParam(required = false)String department
                                                        ){
        DataResponse response = new DataResponse();
        try{
            response.setData(this.userServiceImpl.getAllEmployees(query, department));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Get All Employee Successfully!");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/exportEmployees")
    public ResponseEntity<?> exportEmployee(@RequestParam(required = false)String position,
                                            @RequestParam(required = false)String department
                                                        ){
        try{ 
            byte[] excelBytes = this.excelFormater.exportEmployee(this.userServiceImpl.exportEmployee(position, department));

            ByteArrayResource resource = new ByteArrayResource(excelBytes);

                    return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xlsx")
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .body(resource);
                    // return ResponseEntity.status(HttpStatus.OK).body("ok");
        }catch(Exception e){
            DataResponse response = new DataResponse();
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PostMapping("/importCandidates")
    public ResponseEntity<SuccessResponse> importCandidates(@RequestPart("file")MultipartFile file){
        SuccessResponse response = new SuccessResponse();
        try{
            this.excelFormater.importCandidates(file);
            response.setHttpStatus(HttpStatus.OK);
            response.setHttpStatusCode(200);
            response.setMessage("Candidates Added Successfully ! ");
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setHttpStatusCode(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<SuccessResponse> importEmployees(@RequestPart("file")MultipartFile file){
        SuccessResponse response = new SuccessResponse();
        try{
            response.setHttpStatus(HttpStatus.OK);
            response.setHttpStatusCode(200);
            response.setMessage("Employees Added Successfully ! ");
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setHttpStatusCode(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
