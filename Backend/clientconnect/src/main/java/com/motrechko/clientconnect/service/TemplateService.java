package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.TemplateDTO;
import com.motrechko.clientconnect.exception.TemplateNotFound;
import com.motrechko.clientconnect.mapper.TemplateMapper;
import com.motrechko.clientconnect.model.Template;
import com.motrechko.clientconnect.model.TemplateRequirement;
import com.motrechko.clientconnect.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final UserService userService;
    private final TemplateMapper templateMapper;
//    public TemplateDTO createTemplate(TemplateDTO templateDto) {
//        Template template = templateMapper.map(templateDto);
//        template.setUser(userService.getUser(templateDto.getUserId()));
//        return templateMapper.map(templateRepository.save(template));
//    }

    public TemplateDTO getTemplateById(Long idTemplate) {
        Template template = templateRepository.findById(idTemplate)
                .orElseThrow(() -> new TemplateNotFound(idTemplate));

        return templateMapper.toDto(template);
    }
}
