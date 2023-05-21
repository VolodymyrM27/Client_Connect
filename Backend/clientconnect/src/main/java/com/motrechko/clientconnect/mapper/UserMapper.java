package com.motrechko.clientconnect.mapper;

import com.motrechko.clientconnect.dto.UserDTO;
import com.motrechko.clientconnect.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO map(User userEntity);

    @InheritInverseConfiguration
    User map(UserDTO dto);
}
