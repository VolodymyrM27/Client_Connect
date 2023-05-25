package com.motrechko.clientconnect.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.motrechko.clientconnect.model.ServiceCategory;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * DTO for {@link com.motrechko.clientconnect.model.Requirement}
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class RequirementDto  {
    private Integer id;
    @Size(max = 255)
    private String requirementName;
    @JsonIgnore
    private ServiceCategory category;
}