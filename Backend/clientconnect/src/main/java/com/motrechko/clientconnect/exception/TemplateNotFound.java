package com.motrechko.clientconnect.exception;

import com.motrechko.clientconnect.model.Status;

public class TemplateNotFound extends RuntimeException{
    public TemplateNotFound(Long id){
        super("Template with id " + id + " not found");
    }

    public TemplateNotFound(String email, String categoryName, Status status) {
        super("Cannot find template with status " + status + " a user with email " + email + "In category " + categoryName);
    }
}
