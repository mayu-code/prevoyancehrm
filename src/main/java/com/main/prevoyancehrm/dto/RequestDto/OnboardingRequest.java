package com.main.prevoyancehrm.dto.RequestDto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class OnboardingRequest {

    @Positive(message = "ID must be a positive number")
    private String id;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Role cannot be blank")
    private String role;

    private String joiningDate;

    private String employeeId;

    @Positive(message = "Gross salary must be a positive number")
    private Double grossSalary;
}
