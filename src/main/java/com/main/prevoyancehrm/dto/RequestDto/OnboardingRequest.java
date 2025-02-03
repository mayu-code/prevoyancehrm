package com.main.prevoyancehrm.dto.RequestDto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class OnboardingRequest {

    @Positive(message = "ID must be a positive number")
    private long id;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Role cannot be blank")
    private String role;

    @Positive(message = "Gross salary must be a positive number")
    private Double grossSalary;
}
