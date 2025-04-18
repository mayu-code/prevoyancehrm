package com.main.prevoyancehrm.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDetailResponse {
    private long id;
    private String companyName;
    private String designation;
    private String duration;
    private String annualCTC;

    private String offerLetter;
    private String salarySlip;
    private String reasonOfLeaving;
}
