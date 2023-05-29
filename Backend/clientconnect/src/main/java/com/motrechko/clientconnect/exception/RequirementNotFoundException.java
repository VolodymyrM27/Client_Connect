package com.motrechko.clientconnect.exception;

public class RequirementNotFoundException extends RuntimeException {
    public RequirementNotFoundException(Long id) {
        super("Requirement with id " + id + "not founded");
    }
}
