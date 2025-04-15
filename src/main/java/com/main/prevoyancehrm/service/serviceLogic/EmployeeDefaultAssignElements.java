package com.main.prevoyancehrm.service.serviceLogic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.entities.BalanceLeaves;
import com.main.prevoyancehrm.entities.LeaveType;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.BalanceLeaveServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.LeaveTypeServiceImpl;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

import jakarta.transaction.Transactional;

@Service
public class EmployeeDefaultAssignElements {
    
    @Autowired
    private LeaveTypeServiceImpl leaveTypeServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BalanceLeaveServiceImpl balanceLeaveServiceImpl;

    @Async("taskExecutor")
    @Transactional
    public  void assignAllLeaveTypes(String id){
        List<LeaveType> leaveTypes = this.leaveTypeServiceImpl.getAllLeaveTypes();
        User user2 = this.userServiceImpl.getUserById(id);
        for(LeaveType leaveType :leaveTypes){
            BalanceLeaves balanceLeaves = new BalanceLeaves();
            balanceLeaves.setBalanceLeaves(leaveType.getMaxAllowed());
            balanceLeaves.setLeaveType(leaveType);
            balanceLeaves.setLeavesTaken(0);
            balanceLeaves.setUser(user2);
            this.balanceLeaveServiceImpl.addBalanceLeaves(balanceLeaves);
        }
    }
}
