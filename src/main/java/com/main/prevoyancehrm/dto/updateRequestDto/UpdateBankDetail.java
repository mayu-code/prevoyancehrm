package com.main.prevoyancehrm.dto.updateRequestDto;

import lombok.Data;

@Data
public class UpdateBankDetail {
    private long id;
    private String bankName;
    private String bankAccountNo;
    private String ifscCode;
    private String panNo;
    private String uanNo;
}
