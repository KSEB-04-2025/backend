package com.backend.product.presentation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListResponse {
    private String productId;
    private String label;
    private LocalDateTime uploadDate;
}
