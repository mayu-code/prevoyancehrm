package com.main.prevoyancehrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.prevoyancehrm.entities.BalanceLeaves;

public interface BalanceLeaveRepo extends JpaRepository<BalanceLeaves,Long>{
    
}
