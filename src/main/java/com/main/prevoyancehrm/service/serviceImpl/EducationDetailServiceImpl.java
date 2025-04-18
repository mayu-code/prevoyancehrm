package com.main.prevoyancehrm.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.dto.ResponseDto.EducationDetailResponse;
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

    @Override
    public EducationDetail updateEducationDetail(EducationDetail educationDetail) {
        return this.educationDetailRepo.save(educationDetail);
    }

    @Override
    public void deleteEducationById(long id) {
        this.educationDetailRepo.deleteById(id);
        return ;
    }

    @Override
    public List<EducationDetailResponse> getEducationDetailByUser(String id) {
        return this.educationDetailRepo.findEducationByAndUserId(id);
    }
    
}
