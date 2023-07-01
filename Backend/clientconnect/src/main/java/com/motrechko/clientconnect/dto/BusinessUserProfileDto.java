package com.motrechko.clientconnect.dto;

import com.motrechko.clientconnect.model.Position;
import lombok.Data;

@Data
public class BusinessUserProfileDto {
    private Long id;
    private Long userId;
    private Position position;
    private Long businessId;
}