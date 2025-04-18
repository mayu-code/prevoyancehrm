package com.main.prevoyancehrm.dto.ResponseDto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpResponse {
    private HttpStatus status;
    private int statusCode;
    private String message;
    private String opt;
}