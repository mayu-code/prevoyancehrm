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
import com.main.prevoyancehrm.dto.RequestDto.leavesRequest;
import com.main.prevoyancehrm.dto.responseObjects.DataResponse;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.entities.BalanceLeaves;
import com.main.prevoyancehrm.entities.Leaves;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.BalanceLeaveServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.LeavesServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;
import com.main.prevoyancehrm.service.serviceLogic.EmployeeServiceLogic;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hrExecutive")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class HrExecutiveController {
    
    @Autowired
    private EmployeeServiceLogic employeeServiceLogic;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private LeavesServiceImpl leavesServiceImpl;

    @Autowired 
    private BalanceLeaveServiceImpl balanceLeaveServiceImpl;

     @PostMapping("/addEmployee")
    public ResponseEntity<SuccessResponse> addEmployee(@Valid @RequestBody EmployeeRequestDto employee){

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


    @GetMapping("/getUserByMobileNo")
    public ResponseEntity<DataResponse> getEmployeeByMobileNo(@Valid @RequestParam("mobileNo")String mobileNo){
        DataResponse response = new DataResponse();

        try{
            response.setData(this.userServiceImpl.getUserByMobileNo(mobileNo));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("employee get Successfully!");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<DataResponse> getBalanceLeavesByUserId(@Valid @PathVariable("id")long id){
        DataResponse response = new DataResponse();

        try{
            response.setData(this.balanceLeaveServiceImpl.getAllBalanceLeaves(id));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Balance Leaves get Successfully!");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/addEmployeeLeaves")
    public ResponseEntity<SuccessResponse> addEmployeeLeaves(@Valid @RequestBody leavesRequest request){
        SuccessResponse response = new SuccessResponse();
        Leaves leaves = new Leaves();
        User user = this.userServiceImpl.getEmployeeById(request.getUserId());
        BalanceLeaves balanceLeaves = this.balanceLeaveServiceImpl.getAllBalanceLeaves(request.getUserId());
        try{
            leaves.setUser(user);
            leaves.setEndDate(request.getEndDate());
            leaves.setStartDate(request.getStartDate());
            leaves.setTotalDays(request.getTotalDays());
            leaves.setLeaveType(request.getLeaveType());

            float balance = balanceLeaves.getBalaceLeaves()-request.getTotalDays();
            balanceLeaves.setBalaceLeaves((balance<=0?0:balance));
            balanceLeaves.setLeavesTaken(balanceLeaves.getLeavesTaken()+request.getTotalDays());

            this.balanceLeaveServiceImpl.addBalanceLeaves(balanceLeaves);
            this.leavesServiceImpl.addLeaves(leaves);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Leaves added Successfully!");
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
