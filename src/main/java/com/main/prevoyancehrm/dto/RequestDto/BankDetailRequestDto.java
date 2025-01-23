package com.main.prevoyancehrm.dto.RequestDto;

import lombok.Data;

@Data
public class BankDetailRequestDto {
    private String bankName;
    private String bankAccountNo;
    private String ifscCode;
    private String panNo;
    private String uanNo;
}
