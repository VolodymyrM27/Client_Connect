package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.UserTemplateHistoryDto;
import com.motrechko.clientconnect.mapper.UserTemplateHistoryMapper;
import com.motrechko.clientconnect.model.Business;
import com.motrechko.clientconnect.model.Template;
import com.motrechko.clientconnect.model.User;
import com.motrechko.clientconnect.model.UserTemplateHistory;
import com.motrechko.clientconnect.repository.UserTemplateHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTemplateHistoryService {

    private final UserTemplateHistoryRepository userTemplateHistoryRepository;
    private final UserTemplateHistoryMapper userTemplateHistoryMapper;

    public UserTemplateHistory create(User user, Business business, Template template) {
        validateInputs(user, business, template);

        UserTemplateHistory userTemplateHistory = UserTemplateHistory
                .builder()
                .template(template)
                .usedAt(Instant.now())
                .business(business)
                .user(user)
                .build();

        UserTemplateHistory savedUserTemplateHistory = userTemplateHistoryRepository.save(userTemplateHistory);
        log.info("UserTemplateHistory saved for user {} ,business {} and template {}",
                user.getEmail(),
                business.getBusinessName(),
                template.getId());

        return savedUserTemplateHistory;
    }


    private void validateInputs(User user, Business business, Template template) {
        Assert.notNull(user, "User must not be null");
        Assert.notNull(business, "Business must not be null");
        Assert.notNull(template, "Template must not be null");
    }

    public List<UserTemplateHistoryDto> getUserTemplateHistoryByDate(Long userId){
        List<UserTemplateHistory> existedHistory  = userTemplateHistoryRepository.findByUser_IdOrderByUsedAtDesc(userId);
        return userTemplateHistoryMapper.toDto(existedHistory);
    }

    public List<UserTemplateHistoryDto> getFirst10HistoriesByBusinessIdOrderByUsedAtDesc(Long businessId){
        Pageable pageable = PageRequest.of(0, 10); // Page number starts from 0
        Page<UserTemplateHistory> userTemplateHistoryPage = userTemplateHistoryRepository
                .findFirst10ByBusiness_IdOrderByUsedAtDesc(businessId, pageable);
        List<UserTemplateHistory> userTemplateHistoryList = userTemplateHistoryPage.getContent();
        return userTemplateHistoryMapper.toDto(userTemplateHistoryList);
    }

}
