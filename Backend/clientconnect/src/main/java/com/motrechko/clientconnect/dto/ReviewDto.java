package com.motrechko.clientconnect.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

/**
 * DTO for {@link com.motrechko.clientconnect.model.Review}
 */


@Data
public class ReviewDto {
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long templateId;

    @NotNull
    private Long businessId;

    @NotNull
    @Min(1)
    @Max(5)
    private Short rating;

    @NotBlank
    private String reviewText;

    private Instant reviewedAt;
}


