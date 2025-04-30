package com.main.prevoyancehrm.controller;

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

import com.main.prevoyancehrm.dto.RequestDto.AddNewEducation;
import com.main.prevoyancehrm.dto.responseObjects.DataResponse;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.dto.updateRequestDto.UpdateEducationDetail;
import com.main.prevoyancehrm.dto.updateRequestDto.UpdateExperienceDetail;
import com.main.prevoyancehrm.entities.EducationDetail;
import com.main.prevoyancehrm.entities.ExperienceDetail;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.EducationDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ExperienceDetailsServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.HolidaysServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/hrExecutive")
@CrossOrigin
public class EmployeeController {
    
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ExperienceDetailsServiceImpl experienceDetailsServiceImpl;

    @Autowired
    private EducationDetailServiceImpl educationDetailServiceImpl;

    @Autowired
    private HolidaysServiceImpl holidaysServiceImpl;

    @GetMapping("/AllBirthdays")
    public ResponseEntity<DataResponse> allBirthdays()throws Exception{
        DataResponse response = new DataResponse();
        try{
            response.setData(this.userServiceImpl.employeesBirthday());
            response.setHttpStatus(HttpStatus.OK);
            response.setHttpStatusCode(200);
            response.setMessage("Employee Birthadays get successfully ! ");
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/getUpcommingHolidays")
    public ResponseEntity<?> upcommingHolidays()throws Exception{
        DataResponse response = new DataResponse();
        try{
            response.setData(this.holidaysServiceImpl.getAllHolidays());
            response.setHttpStatus(HttpStatus.OK);
            response.setHttpStatusCode(200);
            response.setMessage("Employee Birthadays get successfully ! ");
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/addNewExperience/{userId}")
    public ResponseEntity<SuccessResponse> addExperienceDetail(@PathVariable("userId")String userId,@Valid @RequestBody UpdateExperienceDetail request){
        SuccessResponse response = new SuccessResponse();
        ExperienceDetail experienceDetail = new ExperienceDetail();
        User user=new User();
        try{
            user = this.userServiceImpl.getUserById(userId);
            experienceDetail.setCompanyName(request.getCompanyName());
            experienceDetail.setDesignation(request.getDesignation());
            experienceDetail.setAnnualCTC(request.getAnnualCTC());
            experienceDetail.setDuration(request.getDuration());
            experienceDetail.setOfferLetter(request.getOfferLetter());
            experienceDetail.setSalarySlip(request.getSalarySlip());
            experienceDetail.setReasonOfLeaving(request.getReasonOfLeaving());
            experienceDetail.setUser(user);

            this.experienceDetailsServiceImpl.addExperienceDetail(experienceDetail);
            
            response.setHttpStatus(HttpStatus.OK);
            response.setHttpStatusCode(200);
            response.setMessage("Experience Added successfully ! ");
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/updateExperienceDetail/{experienceId}")
    public ResponseEntity<SuccessResponse> updateExperienceDetail(@PathVariable("experienceId")long experienceId,@Valid @RequestBody UpdateExperienceDetail request) throws Exception{
        SuccessResponse response = new SuccessResponse();
        ExperienceDetail detail = new ExperienceDetail();
        if(experienceId!=0){
            detail = this.experienceDetailsServiceImpl.getExperienceDetailById(experienceId);

            detail.setCompanyName(request.getCompanyName());
            detail.setDesignation(request.getDesignation());
            detail.setDuration(request.getDuration());
            detail.setAnnualCTC(request.getAnnualCTC());
            detail.setOfferLetter(request.getOfferLetter());
            detail.setSalarySlip(request.getSalarySlip());
            detail.setReasonOfLeaving(request.getReasonOfLeaving());
            this.experienceDetailsServiceImpl.updateExperienceDetail(detail);
        }else{
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("id is must to pass");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));
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

    @PostMapping("/deleteExperience/{id}")
    public ResponseEntity<SuccessResponse> deleteExperienceDetail(@PathVariable("id")long id) throws Exception{
        SuccessResponse response = new SuccessResponse();
        try{ 
            this.experienceDetailsServiceImpl.deleteExperienceDetailById(id);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Delete Experience Detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/deleteEducation/{id}")
    public ResponseEntity<SuccessResponse> deleteEducationDetail(@PathVariable("id")long id)throws Exception{
        SuccessResponse response = new SuccessResponse();
        try{ 
            this.educationDetailServiceImpl.deleteEducationById(id);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Delete Education Detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }


    @PutMapping("/updateEducationDetail/{id}")
    public ResponseEntity<SuccessResponse> updateEducationDetail(@PathVariable("id")Long id,@Valid @RequestBody UpdateEducationDetail request) throws Exception{
        SuccessResponse response = new SuccessResponse();
        EducationDetail detail = new EducationDetail();
        if(id!=0){
            detail = this.educationDetailServiceImpl.getEducationDetailById(id);
            detail.setDegree(request.getDegree());
            detail.setCollege(request.getCollege());
            detail.setField(request.getField());
            detail.setPassingYear(request.getPassingYear());
            detail.setMarksInPercent(request.getMarksInPercent());
            detail.setAdditionalNote(request.getAdditionalNote());
        }else{
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("id must need to past");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));
        }
        try{
            this.educationDetailServiceImpl.addEducationDetail(detail);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("update Education detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/addNewEducation/{userId}")
    public ResponseEntity<?> addNewEducation(@PathVariable("userId")String userId,@Valid @RequestBody AddNewEducation request)throws Exception{
        EducationDetail educationDetail = new EducationDetail();
        User user = this.userServiceImpl.getUserById(userId);
        try{
            System.out.println("ok*************");
            educationDetail.setCollege(request.getCollege());
            educationDetail.setDegree(request.getDegree());
            educationDetail.setField(request.getField());
            educationDetail.setMarksInPercent(request.getMarksInPercent());
            educationDetail.setPassingYear(request.getPassingYear());
            educationDetail.setAdditionalNote(request.getAdditionalNote());
            educationDetail.setUser(user);
            this.educationDetailServiceImpl.addEducationDetail(educationDetail);

            SuccessResponse response = new SuccessResponse(HttpStatus.OK,200,"Education detail Added successfully !");
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    
}
