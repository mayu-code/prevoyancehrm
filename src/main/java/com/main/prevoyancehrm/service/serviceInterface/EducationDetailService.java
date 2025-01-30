package com.main.prevoyancehrm.service.serviceInterface;

import com.main.prevoyancehrm.entities.EducationDetail;

public interface EducationDetailService {
    EducationDetail addEducationDetail(EducationDetail educationDetail);
    EducationDetail getEducationDetailById(long id);
    EducationDetail updateEducationDetail(EducationDetail educationDetail);
    void deleteEducationById(long id);
}
