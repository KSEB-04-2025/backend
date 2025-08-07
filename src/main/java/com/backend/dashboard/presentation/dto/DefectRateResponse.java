package com.backend.dashboard.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DefectRateResponse {
    private String date;         // yyyy-MM-dd
    private long xCount;      // 불량 개수
}