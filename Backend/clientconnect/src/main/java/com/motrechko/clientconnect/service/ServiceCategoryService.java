package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.exception.CategoryNotFoundException;
import com.motrechko.clientconnect.model.ServiceCategory;
import com.motrechko.clientconnect.repository.ServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceCategoryService {

    private final ServiceCategoryRepository serviceCategoryRepository;


    public ServiceCategory getCategoryById(Long id){
        return serviceCategoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public List<ServiceCategory> getAllCategories() {
        return serviceCategoryRepository.findAll();
    }
}
