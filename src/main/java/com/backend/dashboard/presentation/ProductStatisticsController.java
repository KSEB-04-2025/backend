package com.backend.dashboard.presentation;

import com.backend.dashboard.application.ProductStatisticsService;
import com.backend.dashboard.domain.ProductQualityDashboard;
import com.backend.dashboard.presentation.dto.ProductStatisticsResponse;
import com.backend.dashboard.presentation.swagger.ProductStatisticsSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductStatisticsController implements ProductStatisticsSwagger {

    private final ProductStatisticsService service;

    @Override
    public ResponseEntity<List<ProductStatisticsResponse>> getDailyStatistics() {
        return ResponseEntity.ok(service.getDailyStatistics());
    }

    @Override
    public ResponseEntity<List<ProductStatisticsResponse>> getWeeklyStatistics() {
        return ResponseEntity.ok(service.getWeeklyStatisticsByMonth());
    }

    @Override
    public ResponseEntity<List<ProductStatisticsResponse>> getMonthlyStatistics() {
        return ResponseEntity.ok(service.getMonthlyStatisticsByQuarter());
    }
}