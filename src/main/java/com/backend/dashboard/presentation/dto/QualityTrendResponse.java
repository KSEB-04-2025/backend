package com.backend.dashboard.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QualityTrendResponse {
    private String date;           // yyyy-MM-dd
    private long aCount;           // A등급
    private long bCount;           // B등급
    private long totalProducts;  // 전체 개수
}