package com.main.prevoyancehrm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.dto.responseObjects.DataResponse;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;



@RestController
@RequestMapping("/hrExecutive")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class EmployeeController {
    
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/AllBirthdays")
    public ResponseEntity<DataResponse> allBirthdays(){
        DataResponse response = new DataResponse();
        try{
            response.setData(this.userServiceImpl.employeesBirthday());
            response.setHttpStatus(HttpStatus.OK);
            response.setHttpStatusCode(200);
            response.setMessage("Employee Birthadays get successfully ! ");
            return ResponseEntity.of(Optional.of(response));
        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
