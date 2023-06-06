package com.motrechko.clientconnect.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.Instant;

/**
 * DTO for {@link com.motrechko.clientconnect.model.UserTemplateHistory}
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class UserTemplateHistoryDto {
    private Long id;
    private Long userId;
    private Long templateId;
    private Instant usedAt;
    private Long businessId;
    private String businessBusinessName;
    @Size(max = 255)
    private String status;
}