package com.motrechko.clientconnect.exception;

public class TemplateCreationException extends RuntimeException {
    public TemplateCreationException(String errorDuringTemplateCreation, Exception e) {
        super(errorDuringTemplateCreation,e);
    }
}
