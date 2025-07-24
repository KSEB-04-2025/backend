package com.backend.dashboard.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DefectRateResponse {
    private String date;         // yyyy-MM-dd
    private double defectRate;   // 불량률 (%) 소수점
}