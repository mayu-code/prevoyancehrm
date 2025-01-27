package com.main.prevoyancehrm.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.entities.Salary;
import com.main.prevoyancehrm.repository.SalaryRepo;
import com.main.prevoyancehrm.service.serviceInterface.SalaryService;

@Service
public class SalaryServiceImpl implements SalaryService{

    @Autowired
    private SalaryRepo salaryRepo;

    @Override
    public Salary addSalary(Salary salary) {
        return this.salaryRepo.save(salary);
    }

    @Override
    public Salary getSalaryById(long id) {
        return this.salaryRepo.findById(id).get();
    }
    
}
