package com.motrechko.clientconnect.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Size;
import lombok.Data;



/**
 * DTO for {@link com.motrechko.clientconnect.model.TemplateRequirement}
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TemplateRequirementDto  {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long requirementId;
    private String requirementName;
    @Size(max = 255)
    private String requirementValue;
}