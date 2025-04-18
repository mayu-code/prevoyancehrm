package com.main.prevoyancehrm.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalDetailReponse {
    private long id;

    private String totalExperience;
    private String location;
    private String hireSource;
    private String position;
    private String department;
    private String skills;
    private String highestQualification;
    private String joiningDate;
    private String additionalInfo;
    private String offerLetter;
}
