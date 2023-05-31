package com.motrechko.clientconnect.mapper;

import com.motrechko.clientconnect.dto.TerminalDto;
import com.motrechko.clientconnect.model.Terminal;
import org.mapstruct.*;

@Mapper( componentModel = MappingConstants.ComponentModel.SPRING)
public interface TerminalMapper extends EntityMapper<TerminalDto, Terminal> {


    @Override
    @Mapping(source = "businessId", target = "business.id")
    Terminal toEntity(TerminalDto dto);

    @Override
    @InheritInverseConfiguration(name = "toEntity")
    TerminalDto toDto(Terminal entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Terminal partialUpdate(TerminalDto terminalDto, @MappingTarget Terminal terminal);
}