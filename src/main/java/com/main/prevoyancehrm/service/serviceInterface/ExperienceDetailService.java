package com.main.prevoyancehrm.service.serviceInterface;

import java.util.List;

import com.main.prevoyancehrm.dto.ResponseDto.ExperienceDetailResponse;
import com.main.prevoyancehrm.entities.ExperienceDetail;

public interface ExperienceDetailService{
    ExperienceDetail addExperienceDetail(ExperienceDetail experienceDetail);
    ExperienceDetail getExperienceDetailById(long id);
    ExperienceDetail updateExperienceDetail(ExperienceDetail experienceDetail);
    void deleteExperienceDetailById(long id);

    List<ExperienceDetailResponse> getExprerienceByUserId(String id);

}
