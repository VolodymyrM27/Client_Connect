package com.motrechko.clientconnect.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motrechko.clientconnect.model.Template;
import jakarta.validation.constraints.Size;
import lombok.Data;



/**
 * DTO for {@link com.motrechko.clientconnect.model.TemplateRequirement}
 */
@Data
public class TemplateRequirementDto  {
    @JsonIgnore
    private Template template;
    private RequirementDto requirement;
    @Size(max = 255)
    private String requirementValue;
}