package com.motrechko.clientconnect.controller;

import com.motrechko.clientconnect.dto.RequirementDto;
import com.motrechko.clientconnect.dto.ReviewDto;
import com.motrechko.clientconnect.dto.TemplateDTO;
import com.motrechko.clientconnect.model.ServiceCategory;
import com.motrechko.clientconnect.payload.SupportedBusinessResponse;
import com.motrechko.clientconnect.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    private final ServiceCategoryService serviceCategoryService;
    private final RequirementService requirementService;
    private final ReviewService reviewService;
    private final BusinessService businessService;

    @PostMapping()
    public ResponseEntity<TemplateDTO> createTemplate(@RequestBody TemplateDTO templateDto){
        return ResponseEntity.ok(templateService.createTemplate(templateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemplateDTO> updateTemplate(@RequestBody TemplateDTO templateDTO, @PathVariable Long id){
        return ResponseEntity.ok(templateService.updateTemplate(templateDTO,id));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TemplateDTO> getTemplate(@PathVariable Long id){
        return ResponseEntity.ok(templateService.getTemplateDtoById(id));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ServiceCategory>> getCategories(){
        return ResponseEntity.ok(serviceCategoryService.getAllCategories());
    }

    @GetMapping("/categories/{id}/requirement")
    public ResponseEntity<List<RequirementDto>> getRequirementsByCategory(@PathVariable Long id){
        return ResponseEntity.ok(requirementService.getRequirementsByCategory(id));
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewDto> createNewReview( @RequestBody @Valid ReviewDto reviewDTO){
        return ResponseEntity.ok(reviewService.create(reviewDTO));
    }

    @GetMapping("/{id}/supported_businesses")
    public ResponseEntity<List<SupportedBusinessResponse>> getSupportedBusinessOrderByRequirements(@PathVariable Long id){
        return ResponseEntity.ok(businessService.getSupportedBusinesses(id));
    }

}
