package com.main.prevoyancehrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.prevoyancehrm.entities.SalarySlip;

public interface SalarySlipRepo extends JpaRepository<SalarySlip,Long>{
    
}
