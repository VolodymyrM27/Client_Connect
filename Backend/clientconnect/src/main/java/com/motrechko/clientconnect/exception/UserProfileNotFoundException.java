package com.motrechko.clientconnect.exception;

public class UserProfileNotFoundException extends RuntimeException {
    public UserProfileNotFoundException(Long id) {
        super("UserProfile with id: " + id + "not found");
    }
}
