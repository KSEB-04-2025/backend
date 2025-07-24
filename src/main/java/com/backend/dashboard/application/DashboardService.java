package com.backend.dashboard.application;

import com.backend.dashboard.domain.ProductQuality;
import com.backend.dashboard.domain.ProductQualityRepository;
import com.backend.dashboard.exception.DashboardException;
import com.backend.dashboard.presentation.dto.DashboardSummaryResponse;
import com.backend.dashboard.presentation.dto.DefectRateResponse;
import com.backend.dashboard.presentation.dto.QualityTrendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ProductQualityRepository productQualityRepository;

    private static final ZoneId SEOUL_ZONE = ZoneId.of("Asia/Seoul");
    private static final ZoneId UTC_ZONE = ZoneId.of("UTC");
    private static final int DAYS_TO_TRACK = 7;

    private List<ProductQuality> getLastWeekData() {
        LocalDate todaySeoul = LocalDate.now(SEOUL_ZONE);
        LocalDateTime weekAgoSeoul = todaySeoul.minusDays(DAYS_TO_TRACK - 1).atStartOfDay();
        ZonedDateTime weekAgoUTC = weekAgoSeoul.atZone(SEOUL_ZONE).withZoneSameInstant(UTC_ZONE);
        return productQualityRepository.findByUploadDateAfter(weekAgoUTC.toLocalDateTime());
    }

    private LocalDate convertToSeoulDate(LocalDateTime utcDateTime) {
        return utcDateTime.atZone(UTC_ZONE)
                .withZoneSameInstant(SEOUL_ZONE)
                .toLocalDate();
    }

    // 요약
    public DashboardSummaryResponse getSummary() {
        long total = productQualityRepository.count();
        long aCount = productQualityRepository.countByLabel("A");
        long bCount = productQualityRepository.countByLabel("B");

        if (total == 0) {
            throw new DashboardException(
                    "DASHBOARD_DATA_NOT_FOUND",
                    "생산 데이터가 존재하지 않습니다.",
                    HttpStatus.NOT_FOUND
            );
        }
        return new DashboardSummaryResponse(total, aCount, bCount);
    }

    // 7일간 등급별 트렌드
    public List<QualityTrendResponse> getQualityTrends() {
        List<ProductQuality> list = getLastWeekData();
        LocalDate todaySeoul = LocalDate.now(SEOUL_ZONE);

        if (list.isEmpty()) {
            throw new DashboardException(
                    "DASHBOARD_DATA_NOT_FOUND",
                    "최근 7일간 품질 데이터가 존재하지 않습니다.",
                    HttpStatus.NOT_FOUND
            );
        }

        Map<String, long[]> map = new TreeMap<>();
        for (ProductQuality pq : list) {
            LocalDate localDate = convertToSeoulDate(pq.getUploadDate());
            String day = localDate.toString();
            map.putIfAbsent(day, new long[2]);
            if ("A".equalsIgnoreCase(pq.getLabel())) map.get(day)[0]++;
            if ("B".equalsIgnoreCase(pq.getLabel())) map.get(day)[1]++;
        }
        List<QualityTrendResponse> result = new ArrayList<>();
        for (int i = DAYS_TO_TRACK - 1; i >= 0; i--) {
            LocalDate date = todaySeoul.minusDays(i);
            String dateStr = date.toString();
            long[] cnt = map.getOrDefault(dateStr, new long[]{0, 0});
            result.add(new QualityTrendResponse(dateStr, cnt[0], cnt[1]));
        }
        return result;
    }

    // 불량률 ("X" 레이블 기준)
    public List<DefectRateResponse> getDefectRates() {
        List<ProductQuality> list = getLastWeekData();
        LocalDate todaySeoul = LocalDate.now(SEOUL_ZONE);

        if (list.isEmpty()) {
            throw new DashboardException(
                    "DASHBOARD_DEFECT_DATA_NOT_FOUND",
                    "최근 7일간 불량률 데이터를 집계할 수 없습니다.",
                    HttpStatus.NOT_FOUND
            );
        }

        Map<String, int[]> map = new TreeMap<>();
        for (ProductQuality pq : list) {
            LocalDate localDate = convertToSeoulDate(pq.getUploadDate());
            String day = localDate.toString();
            map.putIfAbsent(day, new int[]{0, 0});
            if ("X".equalsIgnoreCase(pq.getLabel())) map.get(day)[0]++;
            map.get(day)[1]++;
        }
        List<DefectRateResponse> result = new ArrayList<>();
        for (int i = DAYS_TO_TRACK - 1; i >= 0; i--) {
            LocalDate date = todaySeoul.minusDays(i);
            String dateStr = date.toString();
            int[] arr = map.getOrDefault(dateStr, new int[]{0, 0});
            double rate = arr[1] == 0 ? 0.0 : ((double) arr[0] / arr[1]) * 100;
            result.add(new DefectRateResponse(dateStr, Math.round(rate * 10.0) / 10.0));
        }
        return result;
    }
}
