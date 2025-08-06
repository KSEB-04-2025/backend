package com.backend.dashboard.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UniformityResponse {
    private String id;
    private String label;
    private int nClusters;
    private double uniformity;
}