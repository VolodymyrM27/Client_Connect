package com.motrechko.clientconnect.payload;

import com.motrechko.clientconnect.dto.UserProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private RegisterRequest registerRequest;
    private UserProfileDTO userProfileDTO;
}
