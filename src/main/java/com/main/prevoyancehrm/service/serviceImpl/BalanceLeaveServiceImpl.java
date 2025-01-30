package com.main.prevoyancehrm.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.entities.BalanceLeaves;
import com.main.prevoyancehrm.repository.BalanceLeaveRepo;
import com.main.prevoyancehrm.service.serviceInterface.BalanceLeavesService;

@Service
public class BalanceLeaveServiceImpl implements BalanceLeavesService{

    @Autowired
    private BalanceLeaveRepo balaceLeaves;
    @Override
    public BalanceLeaves addBalanceLeaves(BalanceLeaves balanceLeaves) {
        return this.balaceLeaves.save(balanceLeaves);
    }
    
}
