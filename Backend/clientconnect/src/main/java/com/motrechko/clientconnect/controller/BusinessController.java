package com.motrechko.clientconnect.controller;

import com.motrechko.clientconnect.dto.BusinessDto;
import com.motrechko.clientconnect.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/business")
public class BusinessController {

    private final BusinessService businessService;
    @PostMapping
    public ResponseEntity<BusinessDto> createBusiness(@RequestBody BusinessDto businessDto){
        return ResponseEntity.ok(businessService.create(businessDto));
    }
}
