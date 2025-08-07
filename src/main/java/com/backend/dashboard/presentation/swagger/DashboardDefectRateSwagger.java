package com.backend.dashboard.presentation.swagger;

import com.backend.dashboard.presentation.dto.DefectRateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Tag(name = "대시보드")
@RequestMapping("/api/admin/dashboard/defect-rate")
public interface DashboardDefectRateSwagger {

    @Operation(summary = "오늘 불량 개수", description = "오늘 하루 동안 발생한 불량 개수를 반환합니다.")
    @GetMapping("/daily")
    ResponseEntity<DefectRateResponse> getTodayDefectCount();

    @Operation(summary = "주별 불량 개수", description = "최근 4주간의 주별 불량 개수를 반환합니다.")
    @GetMapping("/weekly")
    ResponseEntity<List<DefectRateResponse>> getWeeklyDefectCounts();

    @Operation(summary = "월별 불량 개수", description = "최근 3개월간의 월별 불량 개수를 반환합니다.")
    @GetMapping("/monthly")
    ResponseEntity<List<DefectRateResponse>> getMonthlyDefectCounts();
}
