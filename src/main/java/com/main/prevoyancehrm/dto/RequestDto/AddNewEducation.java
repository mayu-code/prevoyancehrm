package com.main.prevoyancehrm.dto.RequestDto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class AddNewEducation {

    @Positive(message = "User ID must be a positive number")
    private String userId;

    @NotBlank(message = "Degree cannot be blank")
    private String degree;

    @NotBlank(message = "College name cannot be blank")
    private String college;

    @NotBlank(message = "Field of study cannot be blank")
    private String field;

    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Invalid passing year format")
    private String passingYear;

    @Pattern(regexp = "^(100|[1-9]?[0-9](\\.\\d{1,2})?)$", message = "Marks should be between 0 and 100 with up to two decimal places")
    private String marksInPercent;

    private String additionalNote;
}
