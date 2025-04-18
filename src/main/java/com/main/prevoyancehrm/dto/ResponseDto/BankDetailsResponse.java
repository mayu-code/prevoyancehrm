package com.main.prevoyancehrm.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetailsResponse {
    private long id;

    private String bankName;
    private String bankAccountNo;
    private String ifscCode;
    private String panNo;
    private String uanNo;
}
