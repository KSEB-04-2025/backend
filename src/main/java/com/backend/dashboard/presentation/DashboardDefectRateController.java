package com.backend.dashboard.presentation;

import com.backend.dashboard.presentation.dto.DefectRateResponse;
import com.backend.dashboard.presentation.swagger.DashboardDefectRateSwagger;
import com.backend.dashboard.application.DashboardDefectRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DashboardDefectRateController implements DashboardDefectRateSwagger {

    private final DashboardDefectRateService service;

    @Override
    public ResponseEntity<DefectRateResponse> getTodayDefectCount() {
        return ResponseEntity.ok(service.getTodayDefectCount());
    }

    @Override
    public ResponseEntity<List<DefectRateResponse>> getWeeklyDefectCounts() {
        return ResponseEntity.ok(service.getWeeklyDefectCounts());
    }

    @Override
    public ResponseEntity<List<DefectRateResponse>> getMonthlyDefectCounts() {
        return ResponseEntity.ok(service.getMonthlyDefectCounts());
    }
}
