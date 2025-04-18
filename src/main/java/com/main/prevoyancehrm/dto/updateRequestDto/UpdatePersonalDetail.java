package com.main.prevoyancehrm.dto.updateRequestDto;

import lombok.Data;

@Data
public class UpdatePersonalDetail {
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String emgMobileNo;
    private String officialEmail;
    private String dob;
    private String adharNo;
    private String image;
    private String presentAddress;
    private String permanentAddress;
}
