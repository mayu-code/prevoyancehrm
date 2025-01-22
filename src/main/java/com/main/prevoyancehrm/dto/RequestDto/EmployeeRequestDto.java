package com.main.prevoyancehrm.dto.RequestDto;

import java.util.List;

import lombok.Data;

@Data
public class EmployeeRequestDto {
    private String email;
    private String password;
    private String contact;
    private String name;
    private String fathersName;
    private String mobileNo;
    private String emgMobileNo;

    private String image;
    private String presentAddress;
    private String permanentAddress;
    private String bankAccountNo;
    private String ifscCode;
    private String possition;

    private List<EducationDetailRequestDTO> educationDetails;
    private List<ProfessionalDetailRequestDTO> professionalDetails;
}
