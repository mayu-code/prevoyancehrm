package com.main.prevoyancehrm.service.serviceInterface;

import java.util.List;

import com.main.prevoyancehrm.entities.LeaveTaken;

public interface LeaveTakenService {
    LeaveTaken addLeaveTaken(LeaveTaken leaveTaken);
    List<LeaveTaken> getLeaveTakenByIdAndEmpId(String userId);
    LeaveTaken getLeaveTakenById(long leaveTakenId);
    LeaveTaken updateLeaveTaken(LeaveTaken leaveTaken);
    void deleteLeaveTakenById(long leaveTakenId);

}
