package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.*;
import com.motrechko.clientconnect.mapper.BusinessUserProfileMapper;
import com.motrechko.clientconnect.payload.EmployeeResponse;
import com.motrechko.clientconnect.payload.NfcScanMessageRequest;
import com.motrechko.clientconnect.exception.AccountRoleException;
import com.motrechko.clientconnect.exception.BusinessNotFoundException;
import com.motrechko.clientconnect.exception.RequirementNotFoundException;
import com.motrechko.clientconnect.exception.UnsupportedRequirementException;
import com.motrechko.clientconnect.mapper.BusinessMapper;
import com.motrechko.clientconnect.mapper.RequirementMapper;
import com.motrechko.clientconnect.model.*;
import com.motrechko.clientconnect.payload.RegisterRequest;
import com.motrechko.clientconnect.payload.SupportedBusinessResponse;
import com.motrechko.clientconnect.repository.BusinessRepository;
import com.motrechko.clientconnect.repository.BusinessSupportedRequirementRepository;
import com.motrechko.clientconnect.repository.BusinessUserProfileRepository;
import com.motrechko.clientconnect.repository.RequirementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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
    private final TerminalService terminalService;
    private final TemplateService templateService;
    private final AuthenticationService authenticationService;
    private final UserProfileService userProfileService;
    private final BusinessUserProfileRepository businessUserProfileRepository;
    private final BusinessUserProfileMapper businessUserProfileMapper;

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
            throw new AccountRoleException(businessDto.getUserId(), Role.USER);
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
            log.error("Unsupported requirement {} for business category {}",
                    requirementDto.getRequirementName(), businessCategory.getCategoryName());
            throw new UnsupportedRequirementException(businessCategory.getCategoryName(), requirementDto.getRequirementName());
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

    public List<RequirementDto> getSupportedRequirements(Long businessId) {
        findBusiness(businessId);
        return businessSupportedRequirementRepository.findByBusiness_Id(businessId)
                .stream()
                .map(BusinessSupportedRequirement::getRequirement)
                .map(requirementMapper::toDto)
                .toList();
    }

    public List<BusinessDto> getAllBusiness() {
        return businessMapper.toDto(businessRepository.findAll());
    }

    public List<BusinessDto> getAllBusinessByCategory(Long idCategory) {
        return businessMapper.toDto(businessRepository.findByCategory_Id(idCategory));
    }

    public Business getBusinessByTerminalId(NfcScanMessageRequest nfcScanMessageRequest) {
        Terminal terminal = terminalService.getTerminalByTerminalUUID(nfcScanMessageRequest.getTerminalUUID());
        return findBusiness(terminal.getBusiness().getId());
    }

    public List<SupportedBusinessResponse> getSupportedBusinesses(Long templateId) {
        Template template = templateService.getTemplateById(templateId);

        List<Requirement> requirements = getRequirementsFromTemplate(template);

        List<Business> businesses = businessRepository.findBySupportedRequirements(requirements);

        return businesses.stream()
                .map(business -> createSupportedBusinessResponse(business, requirements))
                .toList();
    }

    private List<Requirement> getRequirementsFromTemplate(Template template) {
        return template.getTemplateRequirements()
                .stream()
                .map(TemplateRequirement::getRequirement)
                .toList();
    }

    private SupportedBusinessResponse createSupportedBusinessResponse(Business business, List<Requirement> templateRequirements) {
        List<Requirement> matchingRequirements = businessSupportedRequirementRepository.findByBusiness_Id(business.getId())
                .stream()
                .map(BusinessSupportedRequirement::getRequirement)
                .filter(templateRequirements::contains)
                .toList();

        return SupportedBusinessResponse.builder()
                .businessId(business.getId())
                .businessName(business.getBusinessName())
                .businessAddress(business.getAddress())
                .supportedRequirement(requirementMapper.toDto(matchingRequirements))
                .build();
    }

    @Transactional
    public void deleteRequirement(Long businessId, Long requirementId) {
        businessSupportedRequirementRepository.deleteByBusinessIdAndRequirementId(businessId, requirementId);
    }

    public BusinessDto getBusinessByUser(Long userId) {
        return businessMapper.toDto(businessRepository.findByUser_Id(userId));
    }

    public BusinessUserProfileDto createNewEmployee(RegisterRequest employeeRequest
                                            , UserProfileDTO userProfileDTO,
                                                    Long businessId) {
        var response = authenticationService.register(employeeRequest);
        userProfileService.createProfile(response.getId(), userProfileDTO);
        BusinessUserProfile employee = BusinessUserProfile.builder()
                .position(Position.MANAGER)
                .user(userService.getUser(response.getId()))
                .business(findBusiness(businessId))
                .build();
         return businessUserProfileMapper.toDto(businessUserProfileRepository.save(employee));
    }

    public List<EmployeeResponse> getAllBusinessEmployees(Long businessId) {
        List<BusinessUserProfileDto> allBusinessUserProfile =
                businessUserProfileMapper.toDto(businessUserProfileRepository.findByBusiness_Id(businessId));
        List<UserProfileDTO> userProfileDTOList = allBusinessUserProfile
                .stream()
                .map(BusinessUserProfileDto::getUserId)
                .map(userProfileService::getUserProfile)
                .toList();

        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        for (int i = 0; i < allBusinessUserProfile.size(); i++) {
            employeeResponses.add(EmployeeResponse.builder()
                    .businessUserProfileDto(allBusinessUserProfile.get(i))
                    .userProfileDTO(userProfileDTOList.get(i))
                    .build());
        }
        return employeeResponses;
    }

    @Transactional
    public void deleteEmployee(Long businessId, Long employeeId) {
        businessUserProfileRepository.deleteByBusinessIdAndEmployeeId(businessId,employeeId);
        userProfileService.deleteByUserId(employeeId);
        userService.deleteUserById(employeeId);
    }

    public BusinessDto getBusinessById(Long businessId) {
       return businessMapper.toDto(findBusiness(businessId));
    }
}
