package com.main.prevoyancehrm.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.dto.RequestDto.EmployeeRequestDto;
import com.main.prevoyancehrm.dto.responseObjects.DataResponse;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.BalanceLeaveServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;
import com.main.prevoyancehrm.service.serviceLogic.EmployeeServiceLogic;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hrExecutive")
@CrossOrigin
public class HrExecutiveController {
    
    @Autowired
    private EmployeeServiceLogic employeeServiceLogic;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired 
    private BalanceLeaveServiceImpl balanceLeaveServiceImpl;

     @PostMapping("/addCandicate")
    public ResponseEntity<SuccessResponse> addEmployee(@Valid @RequestBody EmployeeRequestDto employee) throws Exception{

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
            throw new Exception(e.getMessage());
        }
    }


    @GetMapping("/getUserByMobileNo")
    public ResponseEntity<DataResponse> getEmployeeByMobileNo(@Valid @RequestParam("mobileNo")String mobileNo) throws Exception{
        DataResponse response = new DataResponse();

        try{
            response.setData(this.userServiceImpl.getUserByMobileNo(mobileNo));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("employee get Successfully!");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/getBalaceLeave/{id}")
    public ResponseEntity<DataResponse> getBalanceLeavesByUserId(@Valid @PathVariable("id")String id) throws Exception{
        DataResponse response = new DataResponse();

        try{
            response.setData(this.balanceLeaveServiceImpl.getAllBalanceLeaves(id));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Balance Leaves get Successfully!");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    
}
