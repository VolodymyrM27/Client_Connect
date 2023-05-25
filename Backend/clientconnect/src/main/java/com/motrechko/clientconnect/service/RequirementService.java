package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.RequirementDto;
import com.motrechko.clientconnect.mapper.RequirementMapper;
import com.motrechko.clientconnect.repository.RequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequirementService {

    private final RequirementRepository requirementRepository;
    private final RequirementMapper requirementMapper;
    public List<RequirementDto> getRequirementsByCategory(Long id) {

        return requirementMapper.toDto(requirementRepository.findByCategory_Id(id));
    }
}
