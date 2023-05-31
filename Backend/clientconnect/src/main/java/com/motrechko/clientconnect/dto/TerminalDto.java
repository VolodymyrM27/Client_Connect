package com.motrechko.clientconnect.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.io.Serializable;

/**
 * DTO for {@link com.motrechko.clientconnect.model.Terminal}
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TerminalDto implements Serializable {
    private Long id;
    private Long businessId;
    private Boolean isContactlessEnabled;
    @Size(max = 255)
    private String uuid;
}