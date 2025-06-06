package com.main.prevoyancehrm.dto.ResponseDto;

import com.main.prevoyancehrm.constants.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
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

    private Role role ;
}
