package com.main.prevoyancehrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.prevoyancehrm.entities.Salary;

public interface SalaryRepo extends JpaRepository<Salary,Long>{
    
}
