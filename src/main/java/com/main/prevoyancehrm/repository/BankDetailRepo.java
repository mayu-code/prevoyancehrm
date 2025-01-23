package com.main.prevoyancehrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.prevoyancehrm.entities.BankDetails;

public interface BankDetailRepo extends JpaRepository<BankDetails,Long>{
    
}
