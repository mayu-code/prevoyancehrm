package com.main.prevoyancehrm.dto.RequestDto;

import lombok.Data;

@Data
public class PersonalDetail {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String mobileNo;
    private String emgMobileNo;
    private String officialEmail;
    private String dob;
    private String adharNo;
    private String image;
    private String presentAddress;
    private String permanentAddress;
}
