package com.motrechko.clientconnect.exception;



public class UnsupportedRequirementException extends RuntimeException {
    public UnsupportedRequirementException(String businessCategory, String category) {
        super("Unsupported requirement " + category + "for business category  " + businessCategory);
    }
}
