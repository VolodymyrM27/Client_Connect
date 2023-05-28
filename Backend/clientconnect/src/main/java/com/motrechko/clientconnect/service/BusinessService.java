package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.BusinessDto;
import com.motrechko.clientconnect.dto.RequirementDto;
import com.motrechko.clientconnect.exception.AccountRoleException;
import com.motrechko.clientconnect.exception.BusinessNotFoundException;
import com.motrechko.clientconnect.exception.RequirementNotFoundException;
import com.motrechko.clientconnect.exception.UnsupportedRequirementException;
import com.motrechko.clientconnect.mapper.BusinessMapper;
import com.motrechko.clientconnect.mapper.RequirementMapper;
import com.motrechko.clientconnect.model.*;
import com.motrechko.clientconnect.repository.BusinessRepository;
import com.motrechko.clientconnect.repository.BusinessSupportedRequirementRepository;
import com.motrechko.clientconnect.repository.RequirementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessService {

    private final BusinessMapper businessMapper;
    private final UserService userService;
    private final BusinessRepository businessRepository;
    private final RequirementMapper requirementMapper;
    private final BusinessSupportedRequirementRepository businessSupportedRequirementRepository;
    private final RequirementRepository requirementRepository;

    @Transactional
    public BusinessDto create(BusinessDto businessDto) {
        log.info("Creating business for user: {}", businessDto.getUserId());

        verifyUserIsBusiness(businessDto);

        Business business = businessMapper.toEntity(businessDto);
        setBusinessTimestamps(business);

        Business savedBusiness = saveBusiness(business);

        return businessMapper.toDto(savedBusiness);
    }

    private void verifyUserIsBusiness(BusinessDto businessDto) {
        User user = userService.getUser(businessDto.getUserId());
        if (Boolean.FALSE.equals(user.getIsBusiness())) {
            log.error("User {} is not a business", businessDto.getUserId());
            throw new AccountRoleException(businessDto.getUserId(), Role.NORMAL_USER);
        }
    }

    private void setBusinessTimestamps(Business business) {
        Instant now = Instant.now();
        business.setCreatedAt(now);
        business.setUpdatedAt(now);
    }

    private Business saveBusiness(Business business) {
        log.debug("Saving business: {}", business.getId());
        return businessRepository.save(business);
    }

    @Transactional
    public Set<RequirementDto> addRequirements(Long businessId, Set<RequirementDto> requirementDtos) {
        if (requirementDtos == null || requirementDtos.isEmpty()) {
            throw new IllegalArgumentException("requirementDtos cannot be null or empty");
        }

        Business business = findBusiness(businessId);
        ServiceCategory businessCategory = business.getCategory();

        Set<Long> requirementIds = requirementDtos.stream()
                .map(RequirementDto::getId)
                .collect(Collectors.toSet());
        List<Requirement> existingRequirements = requirementRepository.findAllById(requirementIds);


        for (RequirementDto requirementDto : requirementDtos) {
            checkRequirementCategory(businessCategory, requirementDto, existingRequirements);
            createAndSaveSupportedRequirement(business, requirementDto);
        }
        log.info("Added {} requirements for business ID {}", requirementDtos.size(), businessId);
        return requirementDtos;
    }

    private Business findBusiness(Long businessId) {
        return businessRepository.findById(businessId)
                .orElseThrow(() -> new BusinessNotFoundException(businessId));
    }

    private void checkRequirementCategory(ServiceCategory businessCategory, RequirementDto requirementDto, List<Requirement> existingRequirements) {
        Requirement existingRequirement = existingRequirements.stream()
                .filter(requirement -> requirement.getId().equals(requirementDto.getId()))
                .findFirst()
                .orElseThrow(() -> new RequirementNotFoundException(requirementDto.getId()));

        if (!existingRequirement.getCategory().equals(businessCategory)) {
            log.error("Unsupported requirement category {} for business category {}",
                    requirementDto.getCategory(), businessCategory);
            throw new UnsupportedRequirementException(businessCategory, requirementDto.getCategory());
        }
    }

    private void createAndSaveSupportedRequirement(Business business, RequirementDto requirementDto) {
        Requirement requirement = requirementMapper.toEntity(requirementDto);

        BusinessSupportedRequirement existingRequirement = businessSupportedRequirementRepository.findByBusinessAndRequirement(business, requirement);

        if (existingRequirement == null) {
            BusinessSupportedRequirement businessSupportedRequirement = BusinessSupportedRequirement
                    .builder()
                    .requirement(requirement)
                    .business(business)
                    .build();

            log.debug("Saving supported requirement for business ID {}", business.getId());
            businessSupportedRequirementRepository.save(businessSupportedRequirement);
        } else {
            log.debug("Requirement already exists for business ID {}, skipping", business.getId());
        }
    }

}
