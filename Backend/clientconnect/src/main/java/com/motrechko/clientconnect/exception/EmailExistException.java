package com.motrechko.clientconnect.exception;

public class EmailExistException extends RuntimeException{
    public EmailExistException(String email){
        super("Email is already exist: " + email);
    }
}
