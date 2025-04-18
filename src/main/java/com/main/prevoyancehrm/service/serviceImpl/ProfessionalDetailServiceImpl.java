package com.main.prevoyancehrm.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.prevoyancehrm.dto.ResponseDto.ProfessionalDetailReponse;
import com.main.prevoyancehrm.entities.ProfessionalDetail;
import com.main.prevoyancehrm.repository.ProfessionalDetailRepo;
import com.main.prevoyancehrm.service.serviceInterface.ProfessinalDetailService;

@Service
public class ProfessionalDetailServiceImpl implements ProfessinalDetailService{

    @Autowired
    private ProfessionalDetailRepo professionalDetailRepo;

    @Override
    public ProfessionalDetail addProfessionalDetail(ProfessionalDetail professionalDetail) {
        return this.professionalDetailRepo.save(professionalDetail);
    }

    @Override
    public ProfessionalDetail getProfessionalDetailById(long id) {
        return this.professionalDetailRepo.findById(id).get();
    }

    @Override
    public ProfessionalDetailReponse getProfessionalDetailByUserId(String id) {
        return this.professionalDetailRepo.findByUserId(id);
    }
    
}
