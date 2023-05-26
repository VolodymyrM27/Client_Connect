package com.motrechko.clientconnect.exception;

public class TemplateDoesNotBelongException extends RuntimeException {
    public TemplateDoesNotBelongException(Long userId, Long templateId) {
        super("The template with the id " + templateId + "does not belong to the user with the id" + userId);
    }
}
