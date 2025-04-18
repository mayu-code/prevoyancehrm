package com.main.prevoyancehrm.service.serviceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.entities.BalanceLeaves;
import com.main.prevoyancehrm.entities.LeaveType;
import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.repository.LeaveTypeRepo;
import com.main.prevoyancehrm.service.serviceInterface.LeavetypeService;

import jakarta.transaction.Transactional;


@Service
public class LeaveTypeServiceImpl implements LeavetypeService {

    @Autowired
    private LeaveTypeRepo leaveTypeRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BalanceLeaveServiceImpl balanceLeaveServiceImpl;

    @Override
    public LeaveType addLeaveTypes(LeaveType leaveType) {
        return this.leaveTypeRepo.save(leaveType);
    }


    @Override
    @Async("taskExecutor")
    @Transactional
    public void assignLeaveTypesToEmployee(LeaveType leaveType) {
        List<User> employees = this.userServiceImpl.getAllEmployees();
        for(User user :employees){
            BalanceLeaves balanceLeaves = new BalanceLeaves();
            balanceLeaves.setBalanceLeaves(leaveType.getMaxAllowed());
            balanceLeaves.setLeavesTaken(0);
            balanceLeaves.setUser(user);
            balanceLeaves.setLeaveType(leaveType);
            this.balanceLeaveServiceImpl.addBalanceLeaves(balanceLeaves);
            balanceLeaves=null;
        }
        
    }


    @Override
    public List<LeaveType> getAllLeaveTypes() {
        return this.leaveTypeRepo.findAll();
    }


    @Override
    public void deleteLeaveType(long id) {
        this.leaveTypeRepo.softDeleteById(id);
        return;
    }
    
}
