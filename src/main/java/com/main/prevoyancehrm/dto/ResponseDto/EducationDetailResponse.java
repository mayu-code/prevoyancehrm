package com.main.prevoyancehrm.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationDetailResponse {
    private long id;
    private String degree;
    private String college;
    private String field;
    private String passingYear;
    private String marksInPercent;
    private String additionalNote;
}
