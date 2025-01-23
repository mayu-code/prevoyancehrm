package com.main.prevoyancehrm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.dto.RequestDto.EmployeeRequestDto;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;
import com.main.prevoyancehrm.service.serviceLogic.EmployeeServiceLogic;

@RestController
@RequestMapping("/hrExecutive")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class EmployeeController {

    @Autowired
    private EmployeeServiceLogic employeeServiceLogic;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/addEmloyee")
    public ResponseEntity<SuccessResponse> addEmployee(@RequestBody EmployeeRequestDto employee){

        SuccessResponse response = new SuccessResponse();
        User user = this.userServiceImpl.getUserByEmail(employee.getPersonalDetail().getEmail());
        if(user!=null){
            response.setHttpStatus(HttpStatus.CREATED);
            response.setMessage("Employee Already present !");
            response.setHttpStatusCode(409);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

       try{
            this.employeeServiceLogic.addEmployee(employee);

            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Employee Added Successfully!");
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
