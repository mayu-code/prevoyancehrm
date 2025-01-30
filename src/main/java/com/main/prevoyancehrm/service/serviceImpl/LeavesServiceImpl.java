package com.main.prevoyancehrm.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.entities.Leaves;
import com.main.prevoyancehrm.repository.LeavesRepo;
import com.main.prevoyancehrm.service.serviceInterface.LeavesService;

@Service
public class LeavesServiceImpl implements LeavesService{

    @Autowired
    private LeavesRepo leavesRepo;

    @Override
    public Leaves addLeaves(Leaves leaves) {
        return this.leavesRepo.save(leaves);
    }

    @Override
    public List<Leaves> getAllLeaves() {
         return null;
    }
    
}
