package com.backend.dashboard.presentation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ProductStatisticsResponse {
    private String date;   // yyyy-MM-dd
    private int acount;
    private int bcount;
}