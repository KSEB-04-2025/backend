package com.backend.dashboard.presentation.swagger;

import com.backend.dashboard.presentation.dto.ProductStatisticsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "통계", description = "제품 등급별 주/월 통계 API")
@RequestMapping("/api/admin/statistics")
public interface ProductStatisticsSwagger {

    @Operation(summary = "일별 통계", description = "오늘 하루 등급별(A/B) 생산량 통계")
    @GetMapping("/daily")
    ResponseEntity<List<ProductStatisticsResponse>> getDailyStatistics();

    @Operation(summary = "주별 통계", description = "최근 한 달(4주) 동안의 주별 등급별(A/B) 생산량 통계")
    @GetMapping("/weekly")
    ResponseEntity<List<ProductStatisticsResponse>> getWeeklyStatistics();

    @Operation(summary = "월별 통계", description = "최근 3개월 동안의 월별 등급별(A/B) 생산량 통계")
    @GetMapping("/monthly")
    ResponseEntity<List<ProductStatisticsResponse>> getMonthlyStatistics();

}