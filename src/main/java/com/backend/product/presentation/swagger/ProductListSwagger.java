package com.backend.product.presentation.swagger;

import com.backend.product.presentation.dto.ProductListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "제품", description = "제품 정보 API")
@RequestMapping("/api/admin/all-products")
public interface ProductListSwagger {

    @Operation(summary = "전체 제품 목록 조회", description = "모든 제품 품질 데이터를 목록으로 조회합니다.")
    @GetMapping
    List<ProductListResponse> getAllProducts();
}
