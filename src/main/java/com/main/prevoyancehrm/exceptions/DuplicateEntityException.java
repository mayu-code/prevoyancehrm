package com.main.prevoyancehrm.exceptions;

public class DuplicateEntityException extends RuntimeException{
    public DuplicateEntityException(String message){
        super(message);
    }
}
