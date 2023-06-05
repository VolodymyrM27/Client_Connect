package com.motrechko.clientconnect.payload;

import com.motrechko.clientconnect.dto.RequirementDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupportedBusinessResponse {
    private Long businessId;
    private String businessName;
    private String businessAddress;
    private List<RequirementDto> supportedRequirement;
}