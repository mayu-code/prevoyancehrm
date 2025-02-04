package com.main.prevoyancehrm.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

import com.main.prevoyancehrm.dto.RequestDto.LeaveTypeRequest;
import com.main.prevoyancehrm.dto.responseObjects.DataResponse;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.entities.LeaveType;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.LeaveTypeServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;


@RestController
@RequestMapping("/superAdmin")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:5174/"})
public class SuperAdminController {

    @Autowired
    private LeaveTypeServiceImpl leaveTypeServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;
    
    @PostMapping("/addLeaveType")
    public ResponseEntity<SuccessResponse> addNewLeaveType(@RequestBody LeaveTypeRequest request){
        SuccessResponse response = new SuccessResponse();
        LeaveType leaveType = new LeaveType();
        try{
            leaveType.setMaxAllowed(request.getTotalLeaves());
            leaveType.setName(request.getLeaveType());
            LeaveType leaveType2  = this.leaveTypeServiceImpl.addLeaveTypes(leaveType);
            CompletableFuture.runAsync(()->this.leaveTypeServiceImpl.assignLeaveTypesToEmployee(leaveType2));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("New Leave Type Added Successfully successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/deleteLeaveType/{id}")
    public ResponseEntity<SuccessResponse> deleteNewLeaveType(@PathVariable("id")long id){
        SuccessResponse response = new SuccessResponse();
    
        try{
            this.leaveTypeServiceImpl.deleteLeaveType(id);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("New Leave Type Added Successfully successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setHttpStatusCode(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getAllLeaveType")
    public ResponseEntity<DataResponse> getAllNewLeaveType(){
        DataResponse response = new DataResponse();
    
        try{
            response.setData(this.leaveTypeServiceImpl.getAllLeaveTypes());
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("New Leave Type Added Successfully successfully !");
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
