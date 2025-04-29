package com.main.prevoyancehrm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.dto.ResponseDto.ExperienceDetailResponse;
import com.main.prevoyancehrm.dto.responseObjects.DataResponse;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.dto.updateRequestDto.UpdateBankDetail;
import com.main.prevoyancehrm.dto.updateRequestDto.UpdateExperienceDetail;
import com.main.prevoyancehrm.dto.updateRequestDto.UpdatePersonalDetail;
import com.main.prevoyancehrm.dto.updateRequestDto.UpdateProfessionalDetail;
import com.main.prevoyancehrm.entities.BankDetails;
import com.main.prevoyancehrm.entities.ExperienceDetail;
import com.main.prevoyancehrm.entities.ProfessionalDetail;
import com.main.prevoyancehrm.entities.Salary;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.mapper.UserMapper;
import com.main.prevoyancehrm.service.serviceImpl.BalanceLeaveServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.BankDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.EducationDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ExperienceDetailsServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.LeaveTypeServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ProfessionalDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.SalaryServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

import jakarta.persistence.EntityNotFoundException;



@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    private final UserServiceImpl userServiceImpl;
    private final BankDetailServiceImpl bankDetailServiceImpl;
    private final ProfessionalDetailServiceImpl professionalDetailServiceImpl;
    private final ExperienceDetailsServiceImpl experienceDetailsServiceImpl;
    private final SalaryServiceImpl salaryServiceImpl;
    private final EducationDetailServiceImpl educationDetailServiceImpl;
    private final BalanceLeaveServiceImpl balanceLeaveServiceImpl;

    public AdminController(UserServiceImpl userServiceImpl,
                         BankDetailServiceImpl bankDetailServiceImpl,
                         ProfessionalDetailServiceImpl professionalDetailServiceImpl,
                         ExperienceDetailsServiceImpl experienceDetailsServiceImpl,
                         SalaryServiceImpl salaryServiceImpl,
                         EducationDetailServiceImpl educationDetailServiceImpl, 
                         BalanceLeaveServiceImpl balanceLeaveServiceImpl,
                         UserMapper userMapper) {
        this.userServiceImpl = userServiceImpl;
        this.bankDetailServiceImpl = bankDetailServiceImpl;
        this.professionalDetailServiceImpl = professionalDetailServiceImpl;
        this.experienceDetailsServiceImpl = experienceDetailsServiceImpl;
        this.salaryServiceImpl = salaryServiceImpl;
        this.educationDetailServiceImpl=educationDetailServiceImpl;
        this.balanceLeaveServiceImpl = balanceLeaveServiceImpl;
    }
    

    // Delete api's *********************************************

    @PostMapping("/deleteCandidate")
    public ResponseEntity<SuccessResponse> deleteCandidate(@RequestBody List<String> ids) throws Exception{
        for(String id:ids){
            this.userServiceImpl.deleteCandidate(id);
        }
        SuccessResponse response = new SuccessResponse(HttpStatus.OK,200,"delete candidates successfully !");
        return ResponseEntity.of(Optional.of(response));
    }


    // get api's ******************************************

    @GetMapping("/getExperienceByUserId/{id}")
    public ResponseEntity<DataResponse> getExperienceUserId(@PathVariable("id")String id) throws Exception{
        DataResponse response = new DataResponse();
        try{
            List<ExperienceDetailResponse> response2 = this.experienceDetailsServiceImpl.getExprerienceByUserId(id);
            response.setData(response2);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("get User Experience successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
    }
    }

    @GetMapping("/getProfesstionlByUserId/{id}")
    public ResponseEntity<DataResponse> getProfesstionlUserId(@PathVariable("id")String id) throws Exception{
        DataResponse response = new DataResponse();
        try{
            response.setData(this.professionalDetailServiceImpl.getProfessionalDetailByUserId(id));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("get professional detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
    }
    }

    @GetMapping("/getEducationByUserId/{id}")
    public ResponseEntity<DataResponse> getEducationByUserId(@PathVariable("id")String id) throws Exception{
        DataResponse response = new DataResponse();
        try{
            response.setData(this.educationDetailServiceImpl.getEducationDetailByUser(id));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("get Education detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
    }
    }

    @GetMapping("/getBankDetailsByUserId/{id}")
    public ResponseEntity<DataResponse> getBankDetailByUserId(@PathVariable("id")String id) throws Exception{
        DataResponse response = new DataResponse();
        try{
            response.setData(this.bankDetailServiceImpl.getBankDetailByUser(id));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("get bank Details successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
    }
    }

    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<DataResponse> getEmployeeById(@PathVariable("id")String id) throws Exception{
        DataResponse response = new DataResponse();
        try{
            response.setData(this.userServiceImpl.getEmployeeById(id));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("get employee successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
    }
    }


    @GetMapping("/getBalanceLeaves/{id}")
    public ResponseEntity<?>getBalanceLeaves(@PathVariable("id")String id)throws Exception{
        try{
            DataResponse response = new DataResponse();
            response.setData(this.balanceLeaveServiceImpl.getAllBalanceLeaves(id));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Balance leaves Get successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            throw new Exception();
        }
    }

    // update api's **********************************
    @PutMapping("/updatePersonalDetail/{userId}")
    public ResponseEntity<SuccessResponse> updatePersonalDetail(@PathVariable("userId")String userId, @RequestBody UpdatePersonalDetail request){
        SuccessResponse response = new SuccessResponse();
        User user = this.userServiceImpl.getUserById(userId);
        if(user==null){
            throw new EntityNotFoundException("User Not found !");
        }
        try{
            
            // if(!user.getEmail().equals(request.getEmail())){
            //     User user2 = this.userServiceImpl.getUserByEmail(request.getEmail());
            //     if(user2!=null){
            //         response.setHttpStatus(HttpStatus.ALREADY_REPORTED);
            //         response.setMessage("Email Already Present !");
            //         response.setHttpStatusCode(208);
            //         return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
            //     }
            // }
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setMobileNo(request.getMobileNo());
            user.setEmgMobileNo(request.getEmgMobileNo());
            user.setOfficialEmail(request.getOfficialEmail());
            user.setDob(request.getDob());
            user.setAdharNo(request.getAdharNo());
            user.setImage(request.getImage());
            user.setPresentAddress(request.getPresentAddress());
            user.setPermanentAddress(request.getPermanentAddress());

            this.userServiceImpl.updateUser(user);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("update personal detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            e.printStackTrace();
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/updateBankDetail/{bankDetailId}")
    public ResponseEntity<SuccessResponse> updateBankDetail(@PathVariable("bankDetailId")long bankDetailId ,@RequestBody UpdateBankDetail request){
        SuccessResponse response = new SuccessResponse();
        BankDetails bankDetails = this.bankDetailServiceImpl.getBankDetailsById(bankDetailId);
        if(bankDetails==null){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Bank Detail Not Found!");
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        try{
            bankDetails.setBankName(request.getBankName());
            bankDetails.setBankAccountNo(request.getBankAccountNo());
            bankDetails.setIfscCode(request.getIfscCode());
            bankDetails.setPanNo(request.getPanNo());
            bankDetails.setUanNo(request.getUanNo());

            this.bankDetailServiceImpl.addBankDetails(bankDetails);
           
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("update Bank detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/updateProfessionalDetail/{professionalId}")
    public ResponseEntity<SuccessResponse> updateProfessionalDetail(@PathVariable("professionalId")long professionalId,@RequestBody UpdateProfessionalDetail request){
        SuccessResponse response = new SuccessResponse();
        ProfessionalDetail detail = this.professionalDetailServiceImpl.getProfessionalDetailById(professionalId);
        if(detail==null){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Professional Detail Not Found!");
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        try{
            Salary salary = new Salary();
            detail.setTotalExperience(request.getTotalExperience());
            detail.setLocation(request.getLocation());
            detail.setHireSource(request.getHireSource());
            detail.setPosition(request.getPosition());
            detail.setDepartment(request.getDepartment());
            detail.setSkills(request.getSkills());
            detail.setHighestQualification(request.getHighestQualification());
            detail.setJoiningDate(request.getJoiningDate());
            detail.setAdditionalInfo(request.getAdditionalInfo());
            detail.setOfferLetter(request.getOfferLetter());

            salary.setGrossSalary(request.getCurrentSalary());
            salary.setUser(detail.getUser());
            
            this.professionalDetailServiceImpl.addProfessionalDetail(detail);
            // this.salaryServiceImpl.addSalary(salary);
            
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("update personal detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            e.printStackTrace();
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/updateExperienceDetail/{experienceId}")
    public ResponseEntity<SuccessResponse> updateExperienceDetail(@PathVariable("experienceId")long experienceId,@RequestBody UpdateExperienceDetail request) throws Exception{
        SuccessResponse response = new SuccessResponse();
        ExperienceDetail detail = new ExperienceDetail();
        if(experienceId!=0){
            detail = this.experienceDetailsServiceImpl.getExperienceDetailById(experienceId);
            detail.setCompanyName(request.getCompanyName());
            detail.setDesignation(request.getDesignation());
            detail.setAnnualCTC(request.getAnnualCTC());
            detail.setDuration(request.getDuration());
            detail.setOfferLetter(request.getOfferLetter());
            detail.setSalarySlip(request.getSalarySlip());
            detail.setReasonOfLeaving(request.getReasonOfLeaving());
            this.experienceDetailsServiceImpl.addExperienceDetail(detail);
        }
        
        try{ 
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Update Experience Detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
