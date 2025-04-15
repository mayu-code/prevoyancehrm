package com.main.prevoyancehrm.exceptions;
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message){
        super(message);
    }
}
