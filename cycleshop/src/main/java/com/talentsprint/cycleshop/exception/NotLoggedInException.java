package com.talentsprint.cycleshop.exception;

public class NotLoggedInException extends RuntimeException {
    public NotLoggedInException(String message){
        super(message);
    }
}
