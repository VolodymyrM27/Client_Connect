package com.motrechko.clientconnect.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.Instant;

/**
 * DTO for {@link com.motrechko.clientconnect.model.UserTemplateHistory}
 */
@Data
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