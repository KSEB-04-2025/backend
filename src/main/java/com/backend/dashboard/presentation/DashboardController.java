package com.backend.dashboard.presentation;

import com.backend.dashboard.application.DashboardService;
import com.backend.dashboard.presentation.dto.DashboardSummaryResponse;
import com.backend.dashboard.presentation.dto.QualityTrendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummaryResponse getSummary() {
        return dashboardService.getSummary();
    }

    @GetMapping("/trend")
    public List<QualityTrendResponse> getQualityTrends() {
        return dashboardService.getQualityTrends();
    }
}