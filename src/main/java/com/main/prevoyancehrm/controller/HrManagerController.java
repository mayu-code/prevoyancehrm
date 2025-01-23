package com.main.prevoyancehrm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.constants.Role;
import com.main.prevoyancehrm.dto.RequestDto.OnboardingRequest;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;


@RestController
@RequestMapping("/hrManager")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class HrManagerController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/onboardEmployee")
    public ResponseEntity<SuccessResponse> onboardEmployee(@RequestHeader("Authorization")String jwt,@RequestBody OnboardingRequest request){
        SuccessResponse response = new SuccessResponse();
        User userEmployee = this.userServiceImpl.getUserByJwt(jwt);

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
}
