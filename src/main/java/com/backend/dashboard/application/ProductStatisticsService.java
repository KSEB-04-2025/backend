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
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductStatisticsService {

    private final ProductQualityDashboardRepository repository;

    // 최근 1달(28일)간 7일 단위 주별 통계 리스트
    public List<ProductStatisticsResponse> getWeeklyStatisticsByMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(27); // 4주(28일), 오늘 포함
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = today.atTime(LocalTime.MAX);

        List<ProductQualityDashboard> data = repository.findAllByUploadDateBetween(startDateTime, endDateTime);

        // 주 단위로 그룹핑 (성능 개선)
        Map<LocalDate, List<ProductQualityDashboard>> weekGrouped = data.stream()
                .collect(Collectors.groupingBy(e -> {
                    LocalDate uploadDate = e.getUploadDate().toLocalDate();
                    long daysBetween = ChronoUnit.DAYS.between(startDate, uploadDate);
                    int weekIndex = (int) (daysBetween / 7);
                    return startDate.plusDays(weekIndex * 7L);
                }));

        // 각 주별 통계 (0 데이터도 포함)
        List<ProductStatisticsResponse> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            LocalDate weekStart = startDate.plusDays(i * 7L);
            List<ProductQualityDashboard> weekData = weekGrouped.getOrDefault(weekStart, Collections.emptyList());
            Map<String, Long> counts = countByLabel(weekData);
            result.add(ProductStatisticsResponse.builder()
                    .date(weekStart.toString())
                    .aCount(counts.get("A").intValue())
                    .bCount(counts.get("B").intValue())
                    .build());
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

        // 월 단위 그룹핑
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
            Map<String, Long> counts = countByLabel(monthData);
            result.add(ProductStatisticsResponse.builder()
                    .date(key)
                    .aCount(counts.get("A").intValue())
                    .bCount(counts.get("B").intValue())
                    .build());
        }

        return result;
    }

    // 오늘 하루(일별)
    public List<ProductStatisticsResponse> getDailyStatistics() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        List<ProductQualityDashboard> dailyList = repository.findAllByUploadDateBetween(start, end);
        Map<String, Long> counts = countByLabel(dailyList);

        return List.of(ProductStatisticsResponse.builder()
                .date(today.toString())
                .aCount(counts.get("A").intValue())
                .bCount(counts.get("B").intValue())
                .build());
    }

    // 공통: 라벨별 카운트
    private Map<String, Long> countByLabel(List<ProductQualityDashboard> data) {
        Map<String, Long> counts = data.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getLabel().toUpperCase(),
                        Collectors.counting()
                ));
        counts.putIfAbsent("A", 0L);
        counts.putIfAbsent("B", 0L);
        return counts;
    }
}
