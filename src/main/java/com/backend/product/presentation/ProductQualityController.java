package com.backend.product.presentation;

import com.backend.product.application.ProductQualityService;
import com.backend.product.presentation.dto.ProductQualityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/product-quality")  // ✅ 인증 가능한 엔드포인트로 변경
@RequiredArgsConstructor
public class ProductQualityController {

    private final ProductQualityService service;

    @GetMapping("/{id}")
    public ProductQualityResponse getProductQuality(@PathVariable String id) {
        return service.getById(id);
    }
}
