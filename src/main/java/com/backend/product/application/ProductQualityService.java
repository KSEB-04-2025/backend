package com.backend.product.application;

import com.backend.core.util.GCSUtil;
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
    private final GCSUtil gcsUtil;

    public ProductQualityResponse getById(String id) {
        ProductQualityDetail pq = repository.findById(id)
                .orElseThrow(() ->
                        new ProductQualityException("P001", "해당 제품 품질 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
                );

        // imgFileId가 null이거나 빈 값일 때 예외 처리
        if (pq.getImgFileId() == null || pq.getImgFileId().isEmpty()) {
            throw new ProductQualityException("P002", "제품 품질 정보에 imgFileId가 존재하지 않습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String imgUrl;
        try {
            // signed url 생성 중 오류 발생 시 예외 처리
            imgUrl = gcsUtil.getSignedUrl(pq.getImgFileId());
        } catch (Exception e) {
            throw new ProductQualityException("P003", "Signed URL 생성에 실패했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ProductQualityResponse.builder()
                .productId(pq.getId())
                .imageUrl(imgUrl)
                .label(pq.getLabel())
                .qualityGrade(convertToGrade(pq.getLabel()))
                .uploadDate(pq.getUploadDate())
                .build();
    }



    private String convertToGrade(String label) {
        return label.equalsIgnoreCase("A") ? "고수율" : "저수율";
    }
}
