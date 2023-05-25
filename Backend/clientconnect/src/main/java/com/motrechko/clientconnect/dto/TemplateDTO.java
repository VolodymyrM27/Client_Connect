package com.motrechko.clientconnect.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

/**
 * DTO for {@link com.motrechko.clientconnect.model.Template}
 */
@Data

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TemplateDTO {
    @NotNull(message = "User Cannot be empty")
    private Long userId;
    @NotNull(message = "category cannot be empty")
    private Long categoryId;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean isPublic;
    private Set<Long> reviewIds;
    @NotNull(message = "Requirements cannot be empty")
    private Set<TemplateRequirementDto> templateRequirements;
    private Set<Long> userTemplateHistoryIds;
}