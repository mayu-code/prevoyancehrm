package com.main.prevoyancehrm.dto.updateRequestDto;

import lombok.Data;

@Data
public class UpdateProfessionalDetail {
    private long id;
    private String totalExperience;
    private String location;
    private String hireSource;
    private String position;
    private String department;
    private String skills;
    private String highestQualification;
    private double currentSalary;
    private String joiningDate;
    private String additionalInfo;
    private String offerLetter;
}
