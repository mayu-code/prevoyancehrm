package com.main.prevoyancehrm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.prevoyancehrm.entities.BalanceLeaves;

public interface BalanceLeaveRepo extends JpaRepository<BalanceLeaves,Long>{
   List<BalanceLeaves> findBalanceLeavesByUserId(long id);
}
