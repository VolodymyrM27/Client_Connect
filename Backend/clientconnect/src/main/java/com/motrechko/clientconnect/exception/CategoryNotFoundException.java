package com.motrechko.clientconnect.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(Long id) {
        super("Service category with id" + id + "not found");
    }
}
