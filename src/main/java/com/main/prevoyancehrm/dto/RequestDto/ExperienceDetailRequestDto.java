package com.main.prevoyancehrm.dto.RequestDto;

import lombok.Data;

@Data
public class ExperienceDetailRequestDto {
    private String companyName;
    private String designation;
    private String duration;
    private String annualCTC;
    private String offerLetter;
    private String salarySlip;
    private String reasonOfLeaving;
}
