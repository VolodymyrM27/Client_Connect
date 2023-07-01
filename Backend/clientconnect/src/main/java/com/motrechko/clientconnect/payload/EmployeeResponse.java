package com.motrechko.clientconnect.payload;

import com.motrechko.clientconnect.dto.BusinessUserProfileDto;
import com.motrechko.clientconnect.dto.UserProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private UserProfileDTO userProfileDTO;
    private BusinessUserProfileDto businessUserProfileDto;
}
