package com.main.prevoyancehrm.dto.RequestDto;

import lombok.Data;

@Data
public class ProfessionalDetailRequestDTO {
    private String organizationName;
    private String designation;
    private String duration;
    private String annualCTC;
    private String reasonOfLeaving;
}
