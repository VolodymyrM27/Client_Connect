package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.TemplateDTO;
import com.motrechko.clientconnect.exception.TemplateCreationException;
import com.motrechko.clientconnect.exception.TemplateDoesNotBelongException;
import com.motrechko.clientconnect.exception.TemplateNotFound;
import com.motrechko.clientconnect.exception.TemplateRequirementEmptyException;
import com.motrechko.clientconnect.mapper.TemplateMapper;
import com.motrechko.clientconnect.model.Status;
import com.motrechko.clientconnect.model.Template;
import com.motrechko.clientconnect.model.TemplateRequirement;
import com.motrechko.clientconnect.repository.TemplateRepository;
import com.motrechko.clientconnect.repository.TemplateRequirementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final TemplateRequirementRepository requirementRepository;
    private final UserService userService;
    private final TemplateMapper templateMapper;

    @Transactional
    public TemplateDTO createTemplate(TemplateDTO templateDto) {
        try{
            Template template = createAndSaveTemplate(templateDto);
            saveTemplateRequirements( template);
            log.info("Successfully created template with id: {}  at: {}", template.getId(), template.getCreatedAt());
            return templateMapper.toDto(template);
        } catch (Exception e){
            log.error("Error during template creating", e);
            throw new TemplateCreationException("Error during template creation: " + e.getMessage(), e);
        }
    }

    private Template createAndSaveTemplate(TemplateDTO templateDTO){
        log.info("Creating and saving template");
        Template template = templateMapper.toEntity(templateDTO);
        template.setStatus(Status.ACTIVE);
        template.setCreatedAt(Instant.now());
        template.setUpdatedAt(Instant.now());
        return templateRepository.save(template);
    }

    private void saveTemplateRequirements(Template template){
        log.info("Saving template requirements");
        Set<TemplateRequirement> templateRequirements = template.getTemplateRequirements();
        if(templateRequirements.isEmpty())
            throw new TemplateRequirementEmptyException();

        templateRequirements.forEach(x -> x.setTemplate(template));
        requirementRepository.saveAll(templateRequirements);
    }


    public TemplateDTO getTemplateById(Long idTemplate) {
        Template template = templateRepository.findByIdAndStatusNot(idTemplate, Status.DELETED)
                .orElseThrow(() -> new TemplateNotFound(idTemplate));
        return templateMapper.toDto(template);
    }
    public Set<TemplateDTO> getTemplatesByUser(Long id){
        return templateMapper.toDtoSet(templateRepository.findByUser_IdAndStatusNot(id,Status.DELETED));
    }

    @Transactional
    public void deleteTemplateByUser(Long userId, Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new TemplateNotFound(templateId));

        if (!template.getUser().getId().equals(userId)) {
            throw new TemplateDoesNotBelongException(userId, templateId);
        }

        template.setStatus(Status.DELETED);
        templateRepository.save(template);
        log.info("Successfully changed the status to {} for the template with id: {} for user with id: {}",Status.DELETED ,templateId, userId);
    }

    public Set<TemplateDTO> getDeletedTemplatesByUser(Long usedId) {
        return templateMapper.toDtoSet(templateRepository.findByUser_IdAndStatus(usedId, Status.DELETED));
    }
}
