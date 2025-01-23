package com.main.prevoyancehrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.prevoyancehrm.entities.ExperienceDetail;

@Repository
public interface ExperienceDetailRepo extends JpaRepository<ExperienceDetail,Long>{
    
}
