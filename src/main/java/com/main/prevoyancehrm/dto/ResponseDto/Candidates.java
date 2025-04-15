package com.main.prevoyancehrm.dto.ResponseDto;

import com.main.prevoyancehrm.constants.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Candidates {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private Role role;
    private String position;
    private String department;
}
