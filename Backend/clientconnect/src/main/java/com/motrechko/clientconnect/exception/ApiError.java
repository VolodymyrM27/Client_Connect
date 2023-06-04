package com.motrechko.clientconnect.exception;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public record ApiError(
        String path,
        String message,
        int statusCode,
        LocalDateTime localDateTime
) {}

