package com.motrechko.clientconnect.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.motrechko.clientconnect.model.ServiceCategory;
import com.motrechko.clientconnect.model.User;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.motrechko.clientconnect.model.Business}
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class BusinessDto {
    private Long id;
    private Long userId;
    @Size(max = 255)
    private String businessName;
    @Size(max = 255)
    private String address;
    private ServiceCategory category;
    private Instant createdAt;
    private Instant updatedAt;
}