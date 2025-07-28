package com.backend.product.application;

import com.backend.product.domain.ProductQualityDetail;
import com.backend.product.domain.ProductQualityDetailRepository;
import com.backend.product.exception.ProductQualityException;
import com.backend.product.presentation.dto.ProductQualityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQualityService {

    private final ProductQualityDetailRepository repository;

    public ProductQualityResponse getById(String id) {
        ProductQualityDetail pq = repository.findById(id)
                .orElseThrow(() ->
                        new ProductQualityException("P001", "해당 제품 품질 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
                );

        return ProductQualityResponse.builder()
                .productId(pq.getId())
                .imageUrl(pq.getImageUrl())
                .label(pq.getLabel())
                .qualityGrade(convertToGrade(pq.getLabel()))
                .uploadDate(pq.getUploadDate())
                .build();
    }

    private String convertToGrade(String label) {
        return label.equalsIgnoreCase("A") ? "고수율" : "저수율";
    }
}
