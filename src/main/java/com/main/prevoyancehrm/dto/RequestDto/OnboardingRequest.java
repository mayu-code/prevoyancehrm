package com.main.prevoyancehrm.dto.RequestDto;

import lombok.Data;

@Data
public class OnboardingRequest {
    private long id;
    private String email;
    private String role;
    private Double grossSalary;
}
