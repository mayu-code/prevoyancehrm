package com.main.prevoyancehrm.dto.RequestDto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class PersonalDetail {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number format")
    private String mobileNo;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid emergency mobile number format")
    private String emgMobileNo;

    @Email(message = "Invalid official email format")
    private String officialEmail;

    @NotBlank(message = "Date of birth cannot be blank")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Date of birth must be in YYYY-MM-DD format")
    private String dob;

    @NotBlank(message = "Aadhar number cannot be blank")
    @Pattern(regexp = "^[2-9]{1}[0-9]{11}$", message = "Invalid Aadhar number format")
    private String adharNo;

    private String image;

    @NotBlank(message = "Present address cannot be blank")
    private String presentAddress;

    @NotBlank(message = "Permanent address cannot be blank")
    private String permanentAddress;
}
