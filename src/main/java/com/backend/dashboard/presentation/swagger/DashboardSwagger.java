package com.backend.dashboard.presentation.swagger;

import com.backend.dashboard.presentation.dto.DashboardSummaryResponse;
import com.backend.dashboard.presentation.dto.QualityTrendResponse;
import com.backend.dashboard.presentation.dto.DefectRateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "대시보드", description = "실시간 품질/불량률/통계 대시보드 API")
@RequestMapping("/api/admin/dashboard")
public interface DashboardSwagger {

    @Operation(summary = "요약 조회", description = "총 생산량, A/B등급 수 조회")
    @GetMapping("/summary")
    ResponseEntity<DashboardSummaryResponse> getSummary();

    @Operation(summary = "일별 품질 추이", description = "최근 7일간 등급별(고수율/저수율) 판별 추이")
    @GetMapping("/trend")
    ResponseEntity<List<QualityTrendResponse>> getQualityTrends();

    @Operation(summary = "불량률 추이", description = "최근 7일간 불량률(%) 추이")
    @GetMapping("/defect-rate")
    ResponseEntity<List<DefectRateResponse>> getDefectRates();
}
