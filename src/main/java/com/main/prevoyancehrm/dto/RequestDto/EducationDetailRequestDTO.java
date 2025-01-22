package com.main.prevoyancehrm.dto.RequestDto;

import lombok.Data;

@Data
public class EducationDetailRequestDTO {
    private String degree;
    private String college;
    private String passingYear;
    private String marksInPercent;
}
