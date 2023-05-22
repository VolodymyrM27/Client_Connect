package com.motrechko.clientconnect.exception;

public class UserProfileAlreadyExistsException extends RuntimeException {
    public UserProfileAlreadyExistsException(Long id) {
        super("User Profile with id" + id + "Already ExistsException");
    }
}
