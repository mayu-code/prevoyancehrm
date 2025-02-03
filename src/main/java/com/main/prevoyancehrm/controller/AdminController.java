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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.dto.responseObjects.DataResponse;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.dto.updateRequestDto.UpdateBankDetail;
import com.main.prevoyancehrm.dto.updateRequestDto.UpdateExperienceDetail;
import com.main.prevoyancehrm.dto.updateRequestDto.UpdatePersonalDetail;
import com.main.prevoyancehrm.dto.updateRequestDto.UpdateProfessionalDetail;
import com.main.prevoyancehrm.entities.BankDetails;
import com.main.prevoyancehrm.entities.ExperienceDetail;
import com.main.prevoyancehrm.entities.ProfessionalDetail;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.BankDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.EducationDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ExperienceDetailsServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ProfessionalDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class AdminController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BankDetailServiceImpl bankDetailServiceImpl;

    @Autowired
    private ProfessionalDetailServiceImpl professionalDetailServiceImpl;

    @Autowired
    private EducationDetailServiceImpl educationDetailServiceImpl;

    @Autowired
    private ExperienceDetailsServiceImpl experienceDetailsServiceImpl;
    
    @PostMapping("/deleteCandidate")
    public ResponseEntity<SuccessResponse> deleteCandidate(@RequestBody List<Long> ids){
        SuccessResponse response = new SuccessResponse();
        try{
            System.out.println(ids.toString());
            for(long id:ids){
                this.userServiceImpl.deleteCandidate(id);
            }
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("delete candidates successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<DataResponse> getEmployeeById(@Valid @PathVariable("id")long id){
        DataResponse response = new DataResponse();
        try{
            response.setData(this.userServiceImpl.getEmployeeById(id));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("get employee successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    }

    @PostMapping("/updatePersonalDetail")
    public ResponseEntity<SuccessResponse> updatePersonalDetail(@Valid @RequestBody UpdatePersonalDetail request){
        SuccessResponse response = new SuccessResponse();
        User user = this.userServiceImpl.getUserById(request.getId());
        if(user==null){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("User Not Found!");
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        try{
            
            if(!user.getEmail().equals(request.getEmail())){
                User user2 = this.userServiceImpl.getUserByEmail(request.getEmail());
                if(user2!=null){
                    response.setHttpStatus(HttpStatus.ALREADY_REPORTED);
                    response.setMessage("Email Already Present !");
                    response.setHttpStatusCode(208);
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
                }
            }
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

            this.userServiceImpl.registerUser(user);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("update personal detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/updateBankDetail")
    public ResponseEntity<SuccessResponse> updateBankDetail(@Valid @RequestBody UpdateBankDetail request){
        SuccessResponse response = new SuccessResponse();
        BankDetails bankDetails = this.bankDetailServiceImpl.getBankDetailsById(request.getId());
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

    @PostMapping("/updateProfessionalDetail")
    public ResponseEntity<SuccessResponse> updateProfessionalDetail(@Valid @RequestBody UpdateProfessionalDetail request){
        SuccessResponse response = new SuccessResponse();
        ProfessionalDetail detail = this.professionalDetailServiceImpl.getProfessionalDetailById(request.getId());
        if(detail==null){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Professional Detail Not Found!");
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        try{
            detail.setTotalExperience(request.getTotalExperience());
            detail.setLocation(request.getLocation());
            detail.setHireSource(request.getHireSource());
            detail.setPosition(request.getPosition());
            detail.setDepartment(request.getDepartment());
            detail.setSkills(request.getSkills());
            detail.setHighestQualification(request.getHighestQualification());
            detail.setCurrentSalary(request.getCurrentSalary());
            detail.setJoiningDate(request.getJoiningDate());
            detail.setAdditionalInfo(request.getAdditionalInfo());
            detail.setOfferLetter(request.getOfferLetter());

            this.professionalDetailServiceImpl.addProfessionalDetail(detail);
            
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("update personal detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping("/updateExperienceDetail")
    public ResponseEntity<SuccessResponse> updateExperienceDetail(@Valid @RequestBody UpdateExperienceDetail request){
        SuccessResponse response = new SuccessResponse();
        ExperienceDetail detail = new ExperienceDetail();
        if(request.getId()!=0){
            detail = this.experienceDetailsServiceImpl.getExperienceDetailById(request.getId());
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
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
