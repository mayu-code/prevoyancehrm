package com.main.prevoyancehrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.prevoyancehrm.entities.LeaveType;

public interface LeaveTypeRepo extends JpaRepository<LeaveType,Long>{
    
}
