package com.backend.dashboard.application;

import com.backend.dashboard.domain.ProductQualityDashboard;
import com.backend.dashboard.domain.ProductQualityDashboardRepository;
import com.backend.dashboard.exception.ProductStatisticsException;
import com.backend.dashboard.presentation.dto.ProductStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductStatisticsService {

    private final ProductQualityDashboardRepository repository;

    // 최근 1달간 7일 단위 주별 통계 리스트
    public List<ProductStatisticsResponse> getWeeklyStatisticsByMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(27); // 오늘 포함 4주 (28일)
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = today.atTime(LocalTime.MAX);

        List<ProductQualityDashboard> data = repository.findAllByUploadDateBetween(startDateTime, endDateTime);

        // 주 단위로 그룹핑
        Map<LocalDate, List<ProductQualityDashboard>> weekGrouped = new LinkedHashMap<>();
        for (int i = 0; i < 4; i++) {
            LocalDate weekStart = startDate.plusDays(i * 7);
            LocalDate weekEnd = weekStart.plusDays(6);
            List<ProductQualityDashboard> weekData = data.stream()
                    .filter(e -> {
                        LocalDate d = e.getUploadDate().toLocalDate();
                        return (!d.isBefore(weekStart)) && (!d.isAfter(weekEnd));
                    })
                    .collect(Collectors.toList());
            weekGrouped.put(weekStart, weekData);
        }

        // 각 주별로 합계 집계
        List<ProductStatisticsResponse> result = new ArrayList<>();
        for (Map.Entry<LocalDate, List<ProductQualityDashboard>> entry : weekGrouped.entrySet()) {
            int aCount = (int) entry.getValue().stream().filter(e -> "A".equalsIgnoreCase(e.getLabel())).count();
            int bCount = (int) entry.getValue().stream().filter(e -> "B".equalsIgnoreCase(e.getLabel())).count();
            result.add(ProductStatisticsResponse.builder()
                    .date(entry.getKey().toString()) // 주 시작일(예: "2025-07-01")
                    .acount(aCount)
                    .bcount(bCount)
                    .build());
        }

        // 데이터가 모두 0인 경우 예외 처리 (예: 모든 주가 acount, bcount 모두 0)
        boolean allZero = result.stream().allMatch(r -> r.getAcount() == 0 && r.getBcount() == 0);
        if (allZero) {
            throw new ProductStatisticsException("NO_WEEKLY_DATA", "최근 한 달(4주) 동안의 통계 데이터가 없습니다.", HttpStatus.NOT_FOUND);
        }

        return result;
    }

    // 최근 3달간 월별 통계 리스트
    public List<ProductStatisticsResponse> getMonthlyStatisticsByQuarter() {
        LocalDate today = LocalDate.now();
        LocalDate startMonth = today.minusMonths(2).withDayOfMonth(1); // 2달 전 1일
        LocalDate endMonth = today.withDayOfMonth(today.lengthOfMonth()); // 이번달 마지막날
        LocalDateTime startDateTime = startMonth.atStartOfDay();
        LocalDateTime endDateTime = endMonth.atTime(LocalTime.MAX);

        List<ProductQualityDashboard> data = repository.findAllByUploadDateBetween(startDateTime, endDateTime);

        // 월 단위로 그룹핑
        Map<String, List<ProductQualityDashboard>> monthGrouped = data.stream()
                .collect(Collectors.groupingBy(e -> {
                    LocalDate d = e.getUploadDate().toLocalDate();
                    return String.format("%d-%02d", d.getYear(), d.getMonthValue());
                }));

        // 최근 3개월 모두 빠짐없이 리턴
        List<ProductStatisticsResponse> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LocalDate m = today.minusMonths(2 - i);
            String key = String.format("%d-%02d", m.getYear(), m.getMonthValue());
            List<ProductQualityDashboard> monthData = monthGrouped.getOrDefault(key, Collections.emptyList());
            int aCount = (int) monthData.stream().filter(e -> "A".equalsIgnoreCase(e.getLabel())).count();
            int bCount = (int) monthData.stream().filter(e -> "B".equalsIgnoreCase(e.getLabel())).count();
            result.add(ProductStatisticsResponse.builder()
                    .date(key) // "2025-07"
                    .acount(aCount)
                    .bcount(bCount)
                    .build());
        }

        boolean allZero = result.stream().allMatch(r -> r.getAcount() == 0 && r.getBcount() == 0);
        if (allZero) {
            throw new ProductStatisticsException("NO_MONTHLY_DATA", "최근 3개월 동안의 통계 데이터가 없습니다.", HttpStatus.NOT_FOUND);
        }

        return result;
    }

    // 일별(오늘만)
    public List<ProductStatisticsResponse> getDailyStatistics() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        List<ProductQualityDashboard> dailyList = repository.findAllByUploadDateBetween(start, end);
        int aCount = (int) dailyList.stream().filter(e -> "A".equalsIgnoreCase(e.getLabel())).count();
        int bCount = (int) dailyList.stream().filter(e -> "B".equalsIgnoreCase(e.getLabel())).count();

        if (aCount == 0 && bCount == 0) {
            throw new ProductStatisticsException("NO_DAILY_DATA", "오늘의 통계 데이터가 없습니다.", HttpStatus.NOT_FOUND);
        }

        return List.of(ProductStatisticsResponse.builder()
                .date(today.toString())
                .acount(aCount)
                .bcount(bCount)
                .build());
    }
}