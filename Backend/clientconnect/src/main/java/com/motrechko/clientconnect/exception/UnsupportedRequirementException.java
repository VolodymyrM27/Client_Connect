package com.motrechko.clientconnect.exception;

import com.motrechko.clientconnect.model.ServiceCategory;

public class UnsupportedRequirementException extends RuntimeException {
    public UnsupportedRequirementException(ServiceCategory businessCategory, ServiceCategory category) {
        super("Unsupported requirement, expected " + businessCategory + "but was " + category);
    }
}
