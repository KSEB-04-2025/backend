package com.backend.product.presentation;

import com.backend.product.application.ProductQualityService;
import com.backend.product.presentation.dto.ProductQualityResponse;
import com.backend.product.presentation.swagger.ProductQualitySwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductQualityController implements ProductQualitySwagger {

    private final ProductQualityService service;

    @Override
    public ProductQualityResponse getProductQuality(String id) {
        return service.getById(id);
    }
}
