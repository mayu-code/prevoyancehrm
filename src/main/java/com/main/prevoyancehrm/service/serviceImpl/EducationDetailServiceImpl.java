package com.main.prevoyancehrm.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.main.prevoyancehrm.entities.EducationDetail;
import com.main.prevoyancehrm.repository.EducationDetailRepo;
import com.main.prevoyancehrm.service.serviceInterface.EducationDetailService;

public class EducationDetailServiceImpl implements EducationDetailService{

    @Autowired
    private EducationDetailRepo educationDetailRepo;

    @Override
    public EducationDetail addEducationDetail(EducationDetail educationDetail) {
        return this.educationDetailRepo.save(educationDetail);
    }
    
}
