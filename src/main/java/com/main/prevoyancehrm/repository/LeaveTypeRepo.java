package com.main.prevoyancehrm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.main.prevoyancehrm.entities.LeaveType;

import jakarta.transaction.Transactional;

public interface LeaveTypeRepo extends JpaRepository<LeaveType,Long>{

    @Query("SELECT l FROM LeaveType l WHERE l.isDelete = false")
    List<LeaveType> findAll();

    @Modifying
    @Transactional
    @Query("UPDATE LeaveType l SET l.isDelete = true, l.deleteAt = CURRENT_TIMESTAMP WHERE l.id = :id")
    int softDeleteById(@Param("id") Long id);
}
