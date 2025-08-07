package com.backend.dashboard.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummaryResponse {
    private long totalProducts;
    private long aQualityProducts;
    private long bQualityProducts;
}