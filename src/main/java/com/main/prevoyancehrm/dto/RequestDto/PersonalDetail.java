package com.main.prevoyancehrm.dto.RequestDto;

import lombok.Data;

@Data
public class PersonalDetail {
    private String email;
    private String name;
    private String fatherName;
    private String mobileNo;
    private String emgMobileNo;

    private String image;
    private String presentAddress;
    private String permanentAddress;
    private String bankAccountNo;
    private String ifscCode;
    private String position;
}
