package com.main.prevoyancehrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.prevoyancehrm.entities.OtpEntry;

import jakarta.transaction.Transactional;

public interface OtpEntryRepo extends JpaRepository<OtpEntry,Long>{
    @Transactional
    void deleteByEmail(String email);
    
    OtpEntry findByEmail(String email);
}
