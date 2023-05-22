package com.motrechko.clientconnect.mapper;


import com.motrechko.clientconnect.dto.UserProfileDTO;
import com.motrechko.clientconnect.model.UserProfile;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileDTO map(UserProfile userProfile);

    @InheritInverseConfiguration
    UserProfile map(UserProfileDTO dto);


}
