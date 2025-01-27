package com.main.prevoyancehrm.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.entities.EducationDetail;
import com.main.prevoyancehrm.repository.EducationDetailRepo;
import com.main.prevoyancehrm.service.serviceInterface.EducationDetailService;

@Service
public class EducationDetailServiceImpl implements EducationDetailService{

    @Autowired
    private EducationDetailRepo educationDetailRepo;

    @Override
    public EducationDetail addEducationDetail(EducationDetail educationDetail) {
        return this.educationDetailRepo.save(educationDetail);
    }

    @Override
    public EducationDetail getEducationDetailById(long id) {
        return this.educationDetailRepo.findById(id).get();
    }
    
}
