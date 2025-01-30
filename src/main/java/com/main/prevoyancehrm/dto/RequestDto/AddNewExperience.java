package com.main.prevoyancehrm.dto.RequestDto;

import lombok.Data;

@Data
public class AddNewExperience {
    private long userId;
    private String companyName;
    private String designation;
    private String duration;
    private String annualCTC;
    private String offerLetter;
    private String salarySlip;
    private String reasonOfLeaving;
}
