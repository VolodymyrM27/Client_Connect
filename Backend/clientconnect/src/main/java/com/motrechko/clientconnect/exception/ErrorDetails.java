package com.motrechko.clientconnect.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDetails {

    private String message;

    public ErrorDetails(String message) {
        this.message = message;
    }
}

