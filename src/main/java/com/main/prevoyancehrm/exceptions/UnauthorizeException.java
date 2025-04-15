package com.main.prevoyancehrm.exceptions;

public class UnauthorizeException extends RuntimeException{
    public UnauthorizeException(String message){
        super(message);
    }
}