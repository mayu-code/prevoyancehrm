package com.main.prevoyancehrm.controller;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.dto.RequestDto.LeaveTypeRequest;
import com.main.prevoyancehrm.dto.responseObjects.DataResponse;
import com.main.prevoyancehrm.dto.responseObjects.SuccessResponse;
import com.main.prevoyancehrm.entities.LeaveType;
import com.main.prevoyancehrm.service.serviceImpl.LeaveTypeServiceImpl;


@RestController
@RequestMapping("/superAdmin")
@CrossOrigin
public class SuperAdminController {

    @Autowired
    private LeaveTypeServiceImpl leaveTypeServiceImpl;

    
    @PostMapping("/addLeaveType")
    public ResponseEntity<SuccessResponse> addNewLeaveType(@RequestBody LeaveTypeRequest request) throws Exception{
        SuccessResponse response = new SuccessResponse();
        LeaveType leaveType = new LeaveType();
        try{
            leaveType.setMaxAllowed(request.getTotalLeaves());
            leaveType.setName(request.getLeaveType());
            leaveType.setDetail(request.getDetail());
            LeaveType leaveType2  = this.leaveTypeServiceImpl.addLeaveTypes(leaveType);
            CompletableFuture.runAsync(()->this.leaveTypeServiceImpl.assignLeaveTypesToEmployee(leaveType2));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("New Leave Type Added Successfully successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
           throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping("/deleteLeaveType/{id}")
    public ResponseEntity<SuccessResponse> deleteNewLeaveType(@PathVariable("id")long id) throws Exception{
        SuccessResponse response = new SuccessResponse();
    
        try{
        
            this.leaveTypeServiceImpl.deleteLeaveType(id);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("New Leave Type Added Successfully successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/getAllLeaveType")
    public ResponseEntity<?> getAllNewLeaveType() throws Exception{
        DataResponse response = new DataResponse();
    
        try{
            response.setData(this.leaveTypeServiceImpl.getAllLeaveTypes());
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("New Leave Type Added Successfully successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PutMapping("/updateLeaveType/{id}")
    public ResponseEntity<SuccessResponse> updateLeaveType(@PathVariable("id")long id, @RequestBody LeaveTypeRequest request) throws Exception{
        SuccessResponse response = new SuccessResponse();
        LeaveType leaveType = this.leaveTypeServiceImpl.getLeaveTypeById(id);
        try{
            leaveType.setMaxAllowed(request.getTotalLeaves());
            leaveType.setName(request.getLeaveType());
            leaveType.setDetail(request.getDetail());
            this.leaveTypeServiceImpl.addLeaveTypes(leaveType);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("New Leave Type Added Successfully successfully !");
            response.setHttpStatusCode(200);
            return ResponseEntity.of(Optional.of(response));

        }catch(Exception e){
           throw new Exception(e.getMessage());
        }
    }
}
