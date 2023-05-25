package com.motrechko.clientconnect.exception;

public class TemplateNotFound extends RuntimeException{
    public TemplateNotFound(Long id){
        super("Template with id " + id + " not found");
    }
}
