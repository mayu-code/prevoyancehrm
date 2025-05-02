package com.main.prevoyancehrm.service.serviceInterface;

import java.util.List;

import com.main.prevoyancehrm.entities.LeaveType;

public interface LeavetypeService {
    LeaveType addLeaveTypes(LeaveType leaveType);
    void assignLeaveTypesToEmployee(LeaveType leaveType);
    List<LeaveType> getAllLeaveTypes();
    void deleteLeaveType(long id);
    List<LeaveType> getDistinLeaveTypes();
}
