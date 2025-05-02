package com.main.prevoyancehrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.prevoyancehrm.entities.LeaveTaken;

public interface LeavesTakenRepo extends JpaRepository<LeaveTaken, Long> {
   
}
