package com.main.prevoyancehrm.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.entities.BankDetails;
import com.main.prevoyancehrm.repository.BankDetailRepo;
import com.main.prevoyancehrm.service.serviceInterface.BankDetailService;


@Service
public class BankDetailServiceImpl implements BankDetailService{

    @Autowired
    private BankDetailRepo bankDetailRepo;

    @Override
    public BankDetails addBankDetails(BankDetails bankDetails) {
        return this.bankDetailRepo.save(bankDetails);
    }
    
}
