package com.main.prevoyancehrm.dto.RequestDto;

import lombok.Data;

@Data
public class AddNewEducation {
    private long userId;
    private String degree;
    private String college;
    private String field;
    private String passingYear;
    private String marksInPercent;
    private String additionalNote;
}
