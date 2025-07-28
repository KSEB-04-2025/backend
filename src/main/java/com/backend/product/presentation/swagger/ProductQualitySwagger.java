package com.backend.product.presentation.swagger;

import com.backend.product.presentation.dto.ProductQualityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "제품")
@RequestMapping("/api/admin/product-quality")
public interface ProductQualitySwagger {

    @Operation(summary = "제품 품질 상세 조회", description = "제품 ID로 상세 품질 정보를 조회합니다.")
    @GetMapping("/{id}")
    ProductQualityResponse getProductQuality(@PathVariable String id);
}