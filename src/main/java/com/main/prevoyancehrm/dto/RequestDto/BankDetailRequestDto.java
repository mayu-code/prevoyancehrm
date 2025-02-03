package com.main.prevoyancehrm.dto.RequestDto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class BankDetailRequestDto {

    @NotBlank(message = "Bank name cannot be blank")
    private String bankName;

    @NotBlank(message = "Bank account number cannot be blank")
    @Pattern(regexp = "^[0-9]{9,18}$", message = "Bank account number must be between 9 to 18 digits")
    private String bankAccountNo;

    @NotBlank(message = "IFSC code cannot be blank")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
    private String ifscCode;

    @NotBlank(message = "PAN number cannot be blank")
    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$", message = "Invalid PAN number format")
    private String panNo;

    @Pattern(regexp = "^[0-9]{12}$", message = "UAN number must be exactly 12 digits")
    private String uanNo;
}
