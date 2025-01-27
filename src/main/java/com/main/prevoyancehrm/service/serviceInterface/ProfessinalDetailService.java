package com.main.prevoyancehrm.service.serviceInterface;

import com.main.prevoyancehrm.entities.ProfessionalDetail;

public interface ProfessinalDetailService {
    ProfessionalDetail addProfessionalDetail(ProfessionalDetail professionalDetail);
    ProfessionalDetail getProfessionalDetailById(long id);
}
