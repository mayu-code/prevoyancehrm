package com.main.prevoyancehrm.dto.updateRequestDto;

import lombok.Data;

@Data
public class UpdateEducationDetail {
    private String degree;
    private String college;
    private String field;
    private String passingYear;
    private String marksInPercent;
    private String additionalNote;
}
