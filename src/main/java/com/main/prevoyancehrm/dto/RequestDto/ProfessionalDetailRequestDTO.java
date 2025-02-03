package com.main.prevoyancehrm.dto.RequestDto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class ProfessionalDetailRequestDTO {

    @NotBlank(message = "Total experience cannot be blank")
    private String totalExperience;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    private String hireSource;

    @NotBlank(message = "Position cannot be blank")
    private String position;

    @NotBlank(message = "Department cannot be blank")
    private String department;

    @NotBlank(message = "Skills cannot be blank")
    private String skills;

    @NotBlank(message = "Highest qualification cannot be blank")
    private String highestQualification;

    @Positive(message = "Current salary must be a positive number")
    private double currentSalary;

    @NotBlank(message = "Joining date cannot be blank")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Joining date must be in DD-MM-YYYY format")
    private String joiningDate;


    private String additionalInfo;

    private String offerLetter;
}
