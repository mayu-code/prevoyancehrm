package com.main.prevoyancehrm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class AdminController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    
    @PostMapping("/deleteCandidate")
    public ResponseEntity<SuccessResponse> deleteCandidate(@RequestBody List<Long> ids){
        SuccessResponse response = new SuccessResponse();
        try{
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
}
