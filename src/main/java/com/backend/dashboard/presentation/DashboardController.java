package com.backend.dashboard.presentation;

import com.backend.dashboard.application.DashboardService;
import com.backend.dashboard.presentation.dto.*;
import com.backend.dashboard.presentation.swagger.DashboardSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DashboardController implements DashboardSwagger {

    private final DashboardService dashboardService;

    @Override
    public ResponseEntity<DashboardSummaryResponse> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @Override
    public ResponseEntity<List<QualityTrendResponse>> getQualityTrends() {
        return ResponseEntity.ok(dashboardService.getQualityTrends());
    }

}
