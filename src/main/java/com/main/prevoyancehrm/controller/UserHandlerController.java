package com.main.prevoyancehrm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.dto.ResponseDto.ExperienceDetailResponse;
import com.main.prevoyancehrm.dto.ResponseDto.UserResponse;
import com.main.prevoyancehrm.dto.responseObjects.DataResponse;
import com.main.prevoyancehrm.jwtSecurity.JwtProvider;
import com.main.prevoyancehrm.service.serviceImpl.BalanceLeaveServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.BankDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.EducationDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ExperienceDetailsServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.LeaveTypeServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.ProfessionalDetailServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserHandlerController {
    
    private final UserServiceImpl userServiceImpl;
    private final BalanceLeaveServiceImpl balanceLeaveServiceImpl;
    private final ProfessionalDetailServiceImpl professionalDetailServiceImpl;
    private final EducationDetailServiceImpl educationDetailServiceImpl;
    private final ExperienceDetailsServiceImpl experienceDetailsServiceImpl;
    private final BankDetailServiceImpl bankDetailServiceImpl;
    private final LeaveTypeServiceImpl leaveTypeServiceImpl;

    @Autowired
    public UserHandlerController(
        UserServiceImpl userServiceImpl,
        BalanceLeaveServiceImpl balanceLeaveServiceImpl,
        ProfessionalDetailServiceImpl professionalDetailServiceImpl,
        EducationDetailServiceImpl educationDetailServiceImpl,
        ExperienceDetailsServiceImpl experienceDetailsServiceImpl,
        BankDetailServiceImpl bankDetailServiceImpl,
        LeaveTypeServiceImpl leaveTypeServiceImpl
    ) {
        this.userServiceImpl = userServiceImpl;
        this.balanceLeaveServiceImpl = balanceLeaveServiceImpl;
        this.professionalDetailServiceImpl = professionalDetailServiceImpl;
        this.educationDetailServiceImpl = educationDetailServiceImpl;
        this.experienceDetailsServiceImpl = experienceDetailsServiceImpl;
        this.bankDetailServiceImpl = bankDetailServiceImpl;
        this.leaveTypeServiceImpl = leaveTypeServiceImpl;
    }

    @GetMapping("/getProfile")
    public ResponseEntity<DataResponse> getProfile(@RequestHeader("Authorization")String jwt){
        DataResponse response = new DataResponse();
        if(JwtProvider.isTokenExpired(jwt)){
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setMessage("you session is expired ! please login again");
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
        }
        
        UserResponse user = new UserResponse();
        try{
            user = this.userServiceImpl.getUserProfile(jwt);
            response.setHttpStatus(HttpStatus.OK);
            response.setData(user);
            response.setMessage("User profile get Successfully!");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getExperienceByUser")
    public ResponseEntity<DataResponse> getExperienceUserId(@RequestHeader("Authorization")String jwt)throws Exception{
        try{
            UserResponse user = new UserResponse();
            user = this.userServiceImpl.getUserProfile(jwt);
            DataResponse response = new DataResponse();
            List<ExperienceDetailResponse> response2 = this.experienceDetailsServiceImpl.getExprerienceByUserId(user.getId());
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

    @GetMapping("/getProfesstionlByUser")
    public ResponseEntity<DataResponse> getProfesstionlUserId(@RequestHeader("Authorization")String jwt)throws Exception{
        try{
            UserResponse user = new UserResponse();
            user = this.userServiceImpl.getUserProfile(jwt);
            DataResponse response = new DataResponse();
            response.setData(this.professionalDetailServiceImpl.getProfessionalDetailByUserId(user.getId()));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("get professional detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
    }
    }

    @GetMapping("/getEducationByUser")
    public ResponseEntity<DataResponse> getEducationByUserId(@RequestHeader("Authorization")String jwt)throws Exception{
        try{
            UserResponse user = new UserResponse();
            user = this.userServiceImpl.getUserProfile(jwt);
            DataResponse response = new DataResponse();
            response.setData(this.educationDetailServiceImpl.getEducationDetailByUser(user.getId()));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("get Education detail successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
    }
    }

    @GetMapping("/getBankDetailsByUser")
    public ResponseEntity<DataResponse> getBankDetailByUserId(@RequestHeader("Authorization")String jwt)throws Exception{
        try{
            UserResponse user = new UserResponse();
            user = this.userServiceImpl.getUserProfile(jwt);
            DataResponse response = new DataResponse();
            response.setData(this.bankDetailServiceImpl.getBankDetailByUser(user.getId()));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("get bank Details successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
    }
    }

    @GetMapping("/getBalanceLeaves")
    public ResponseEntity<?>getBalanceLeaves(@RequestHeader("Authorization")String jwt)throws Exception{
        try{
            UserResponse user = new UserResponse();
            user = this.userServiceImpl.getUserProfile(jwt);
            DataResponse response = new DataResponse();
            response.setData(this.balanceLeaveServiceImpl.getAllBalanceLeaves(user.getId()));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Balance leaves Get successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            throw new Exception();
        }
    }
    @GetMapping("/getEmpBalanceLeaves/{userId}")
    public ResponseEntity<?>getBalanceLeavesEmpId(@PathVariable("userId") String userId)throws Exception{
        try{
            DataResponse response = new DataResponse();
            response.setData(this.balanceLeaveServiceImpl.getAllBalanceLeaves(userId));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Balance leaves Get successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            throw new Exception();
        }
    }

    @GetMapping("/getDistinctLeaveTypes")
    public ResponseEntity<?>getDistinctLeaveTypes()throws Exception{
        try{
            DataResponse response = new DataResponse();
            response.setData(this.leaveTypeServiceImpl.getDistinLeaveTypes());
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Balance leaves Get successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
