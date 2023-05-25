package com.motrechko.clientconnect.mapper;


import com.motrechko.clientconnect.dto.UserProfileDTO;
import com.motrechko.clientconnect.model.UserProfile;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileMapper {
    UserProfileDTO map(UserProfile userProfile);

    @InheritInverseConfiguration
    UserProfile map(UserProfileDTO dto);


}
