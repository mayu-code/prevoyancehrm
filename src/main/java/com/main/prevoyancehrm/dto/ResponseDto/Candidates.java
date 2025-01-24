package com.main.prevoyancehrm.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Candidates {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String position;
    private String department;
}
