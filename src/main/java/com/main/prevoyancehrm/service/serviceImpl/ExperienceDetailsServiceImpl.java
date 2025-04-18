package com.main.prevoyancehrm.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.dto.ResponseDto.ExperienceDetailResponse;
import com.main.prevoyancehrm.entities.ExperienceDetail;
import com.main.prevoyancehrm.repository.ExperienceDetailRepo;
import com.main.prevoyancehrm.service.serviceInterface.ExperienceDetailService;

@Service
public class ExperienceDetailsServiceImpl implements ExperienceDetailService{

    @Autowired
    private ExperienceDetailRepo experienceDetailRepo;

    @Override
    public ExperienceDetail addExperienceDetail(ExperienceDetail experienceDetail) {
        return this.experienceDetailRepo.save(experienceDetail);
    }

    @Override
    public ExperienceDetail getExperienceDetailById(long id) {
        return this.experienceDetailRepo.findById(id).get();
    }

    @Override
    public ExperienceDetail updateExperienceDetail(ExperienceDetail experienceDetail) {
         return this.experienceDetailRepo.save(experienceDetail);
    }

    @Override
    public void deleteExperienceDetailById(long id) {
        this.experienceDetailRepo.deleteById(id);   
        return;
    }

    @Override
    public List<ExperienceDetailResponse> getExprerienceByUserId(String id) {
        return this.experienceDetailRepo.findAllByUserId(id);
    }
    
}
