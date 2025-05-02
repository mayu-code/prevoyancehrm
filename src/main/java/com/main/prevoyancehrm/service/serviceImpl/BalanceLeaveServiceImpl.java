package com.main.prevoyancehrm.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.dto.ResponseDto.BalaceLeavesResponse;
import com.main.prevoyancehrm.entities.BalanceLeaves;
import com.main.prevoyancehrm.repository.BalanceLeaveRepo;
import com.main.prevoyancehrm.service.serviceInterface.BalanceLeavesService;

import jakarta.transaction.Transactional;

@Service
public class BalanceLeaveServiceImpl implements BalanceLeavesService{

    @Autowired
    private BalanceLeaveRepo balaceLeavesRepo;

    @Transactional
    @Override
    public BalanceLeaves addBalanceLeaves(BalanceLeaves balanceLeaves) {
        return this.balaceLeavesRepo.save(balanceLeaves);
    }
    @Override
    public List<BalaceLeavesResponse> getAllBalanceLeaves(String id) {
        return this.balaceLeavesRepo.findAllByUserId(id);
    }
    @Override
    public BalanceLeaves getBalanceLeaveByIdAndEmpId(long balanceLeaveId, String userId) {
        return this.balaceLeavesRepo.findByBalanceLeaveIdAndEmpId(balanceLeaveId, userId).orElse(null);
    }
    
}
