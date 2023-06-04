package com.motrechko.clientconnect.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtTokenException extends AuthenticationException {
    public InvalidJwtTokenException(){
        super("Invalid Jwt-token, please log in again");
    }
}
