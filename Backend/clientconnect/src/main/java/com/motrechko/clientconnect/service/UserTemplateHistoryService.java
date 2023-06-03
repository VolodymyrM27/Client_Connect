package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.model.Business;
import com.motrechko.clientconnect.model.Template;
import com.motrechko.clientconnect.model.User;
import com.motrechko.clientconnect.model.UserTemplateHistory;
import com.motrechko.clientconnect.repository.UserTemplateHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTemplateHistoryService {

    private final UserTemplateHistoryRepository userTemplateHistoryRepository;

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
}
