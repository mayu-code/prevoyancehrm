package com.main.prevoyancehrm.dto.responseObjects;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Data;

@Data
public class SuccessResponse {
    private String message;
    private HttpStatus httpStatus;
    private HttpStatusCode httpStatusCode;
}
