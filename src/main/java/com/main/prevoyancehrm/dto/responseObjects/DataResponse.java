package com.main.prevoyancehrm.dto.responseObjects;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class DataResponse {
    private String message;
    private HttpStatus httpStatus;
    private int httpStatusCode;
    private Object data;
}
