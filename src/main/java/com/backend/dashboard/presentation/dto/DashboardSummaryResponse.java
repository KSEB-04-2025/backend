package com.backend.dashboard.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardSummaryResponse {
    private long totalProducts;
    private long aQualityProducts;
    private long bQualityProducts;
}