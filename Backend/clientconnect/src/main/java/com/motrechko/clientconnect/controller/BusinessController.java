package com.motrechko.clientconnect.controller;

import com.motrechko.clientconnect.dto.BusinessDto;
import com.motrechko.clientconnect.dto.RequirementDto;
import com.motrechko.clientconnect.dto.ReviewDto;
import com.motrechko.clientconnect.dto.TerminalDto;
import com.motrechko.clientconnect.payload.BusinessStatisticResponse;
import com.motrechko.clientconnect.service.BusinessService;
import com.motrechko.clientconnect.service.BusinessStatisticService;
import com.motrechko.clientconnect.service.ReviewService;
import com.motrechko.clientconnect.service.TerminalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/business")
public class BusinessController {

    private final BusinessService businessService;
    private final TerminalService terminalService;
    private final ReviewService reviewService;
    private final BusinessStatisticService businessStatisticService;

    @GetMapping
    public ResponseEntity<List<BusinessDto>> getAllBusiness(){
        return ResponseEntity.ok(businessService.getAllBusiness());
    }

    @GetMapping("/category/{idCategory}")
    public ResponseEntity<List<BusinessDto>> getAllBusinessByCategory(@PathVariable Long idCategory){
        return ResponseEntity.ok(businessService.getAllBusinessByCategory(idCategory));
    }


    @PostMapping
    public ResponseEntity<BusinessDto> createBusiness(@RequestBody BusinessDto businessDto){
        return ResponseEntity.ok(businessService.create(businessDto));
    }

    @PostMapping("/{businessId}/requirements")
    public ResponseEntity<Set<RequirementDto>> addRequirements(@PathVariable Long businessId, @RequestBody Set<RequirementDto> requirementDtos){
        return ResponseEntity.ok(businessService.addRequirements( businessId, requirementDtos));
    }

    @GetMapping("/{businessId}/requirements")
    public ResponseEntity<List<RequirementDto>> getSupportedRequirements(@PathVariable Long businessId){
        return ResponseEntity.ok(businessService.getSupportedRequirements(businessId));
    }

    @PostMapping("/terminals")
    public ResponseEntity<TerminalDto> addNewTerminals(@RequestBody TerminalDto terminalDto){
        return ResponseEntity.ok(terminalService.create(terminalDto));
    }

    @GetMapping("/{businessId}/reviews")
    public ResponseEntity<List<ReviewDto>> getAllBusinessReviews(@PathVariable Long businessId){
        return ResponseEntity.ok(reviewService.getAllReviewsByBusiness(businessId));
    }

    @GetMapping("/{id}/statistic")
    private ResponseEntity<BusinessStatisticResponse> getBusinessStatistic(@PathVariable Long id){
        return ResponseEntity.ok(businessStatisticService.getStatistic(id));
    }
}
