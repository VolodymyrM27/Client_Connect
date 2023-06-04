package com.motrechko.clientconnect.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenExpiredException extends AuthenticationException {
    public JwtTokenExpiredException(){
        super("Jwt token is already expired");
    }
}
