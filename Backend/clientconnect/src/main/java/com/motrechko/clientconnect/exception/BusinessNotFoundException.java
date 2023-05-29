package com.motrechko.clientconnect.exception;

public class BusinessNotFoundException extends RuntimeException {
    public BusinessNotFoundException(Long businessId) {
        super("Business with id: " + businessId+ " not found");
    }
}
