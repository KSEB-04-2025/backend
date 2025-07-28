package com.backend.product.presentation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductQualityResponse {

    private String productId;
    private String imageUrl;
    private String label;          // A / B
    private String qualityGrade;   // 고수율 / 저수율
    private LocalDateTime uploadDate; // 통일된 필드명
}
