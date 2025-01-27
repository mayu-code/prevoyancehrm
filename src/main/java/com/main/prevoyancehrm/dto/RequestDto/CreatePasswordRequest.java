package com.main.prevoyancehrm.dto.RequestDto;

import lombok.Data;

@Data
public class CreatePasswordRequest {
    private String email;
    private String password;
    private String confirmPassword;
}
