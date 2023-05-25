package com.motrechko.clientconnect.mapper;

import com.motrechko.clientconnect.dto.UserDTO;
import com.motrechko.clientconnect.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDTO map(User userEntity);

    @InheritInverseConfiguration
    User map(UserDTO dto);
}
