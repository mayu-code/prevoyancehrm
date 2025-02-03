package com.main.prevoyancehrm.dto.RequestDto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class AddNewExperience {

    @Positive(message = "User ID must be a positive number")
    private long userId;

    @NotBlank(message = "Company name cannot be blank")
    private String companyName;

    @NotBlank(message = "Designation cannot be blank")
    private String designation;

    @NotBlank(message = "Duration cannot be blank")
    private String duration;

    @Pattern(regexp = "^[1-9]\\d*(\\.\\d{1,2})?$", message = "Annual CTC must be a positive number with up to two decimal places")
    private String annualCTC;

    private String offerLetter;

    private String salarySlip;

    private String reasonOfLeaving;
}
