package com.main.prevoyancehrm.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.entities.LeaveTaken;
import com.main.prevoyancehrm.repository.LeavesTakenRepo;
import com.main.prevoyancehrm.service.serviceInterface.LeaveTakenService;


@Service
public class LeaveTakenServiceImpl implements LeaveTakenService {

    @Autowired LeavesTakenRepo leavesTakenRepo;

    @Override
    public LeaveTaken addLeaveTaken(LeaveTaken leaveTaken) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addLeaveTaken'");
    }

    @Override
    public List<LeaveTaken> getLeaveTakenByIdAndEmpId(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLeaveTakenByIdAndEmpId'");
    }

    @Override
    public LeaveTaken getLeaveTakenById(long leaveTakenId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLeaveTakenById'");
    }

    @Override
    public LeaveTaken updateLeaveTaken(LeaveTaken leaveTaken) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateLeaveTaken'");
    }

    @Override
    public void deleteLeaveTakenById(long leaveTakenId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteLeaveTakenById'");
    }
    // Assuming you have a repository for LeaveTaken
    
}
