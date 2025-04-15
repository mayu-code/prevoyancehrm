package com.main.prevoyancehrm.dto.responseObjects;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse {
    private HttpStatus httpStatus;
    private int httpStatusCode;
    private String message;
}
