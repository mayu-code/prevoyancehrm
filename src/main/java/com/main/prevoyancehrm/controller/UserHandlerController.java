package com.main.prevoyancehrm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.dto.responseObjects.DataResponse;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/hrExecutive")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class UserHandlerController {
    
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("getProfile")
    public ResponseEntity<DataResponse> getProfile(@RequestHeader("Authorization")String jwt){
        DataResponse response = new DataResponse();
        User user = new User();
        user = this.userServiceImpl.getUserByJwt(jwt);
        try{
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
}
