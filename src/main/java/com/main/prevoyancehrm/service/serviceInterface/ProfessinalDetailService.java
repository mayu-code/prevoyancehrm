package com.main.prevoyancehrm.service.serviceInterface;

import com.main.prevoyancehrm.dto.ResponseDto.ProfessionalDetailReponse;
import com.main.prevoyancehrm.entities.ProfessionalDetail;

public interface ProfessinalDetailService {
    ProfessionalDetail addProfessionalDetail(ProfessionalDetail professionalDetail);
    ProfessionalDetail getProfessionalDetailById(long id);

    ProfessionalDetailReponse getProfessionalDetailByUserId(String id);
}
