package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.BusinessDto;
import com.motrechko.clientconnect.exception.AccountRoleException;
import com.motrechko.clientconnect.mapper.BusinessMapper;
import com.motrechko.clientconnect.model.Business;
import com.motrechko.clientconnect.model.Role;
import com.motrechko.clientconnect.model.User;
import com.motrechko.clientconnect.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessService {

    private final BusinessMapper businessMapper;
    private final UserService userService;
    private final BusinessRepository businessRepository;

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
}
