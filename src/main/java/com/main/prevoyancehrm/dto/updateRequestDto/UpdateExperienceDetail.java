package com.main.prevoyancehrm.dto.updateRequestDto;

import lombok.Data;

@Data
public class UpdateExperienceDetail {
    private long userId;
    private long id;
    private String companyName;
    private String designation;
    private String duration;
    private String annualCTC;
    private String offerLetter;
    private String salarySlip;
    private String reasonOfLeaving;
}
