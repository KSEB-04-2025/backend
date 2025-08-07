package com.backend.dashboard.application;

import com.backend.dashboard.domain.ProductQualityDashboardRepository;
import com.backend.dashboard.exception.DefectRateException;
import com.backend.dashboard.presentation.dto.DefectRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardDefectRateService {

    private final ProductQualityDashboardRepository repository;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DefectRateResponse getTodayDefectCount() {
        try {
            LocalDate today = LocalDate.now();
            LocalDateTime start = today.atStartOfDay();
            LocalDateTime end = start.plusDays(1);
            long count = repository.countByLabelAndUploadDateBetween("X", start, end);
            return new DefectRateResponse(today.format(DATE_FORMAT), count);
        } catch (Exception e) {
            throw new DefectRateException(
                    "DEFECT_001",
                    "오늘의 불량 데이터를 집계하는 중 오류가 발생했습니다.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<DefectRateResponse> getWeeklyDefectCounts() {
        try {
            List<DefectRateResponse> result = new ArrayList<>();
            LocalDate today = LocalDate.now();
            for (int i = 3; i >= 0; i--) {
                LocalDate start = today.minusWeeks(i).with(java.time.DayOfWeek.MONDAY);
                LocalDate end = start.plusDays(7);
                long count = repository.countByLabelAndUploadDateBetween("X", start.atStartOfDay(), end.atStartOfDay());
                result.add(new DefectRateResponse(start.format(DATE_FORMAT), count));
            }
            return result;
        } catch (Exception e) {
            throw new DefectRateException(
                    "DEFECT_002",
                    "주간 불량 데이터를 집계하는 중 오류가 발생했습니다.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<DefectRateResponse> getMonthlyDefectCounts() {
        try {
            List<DefectRateResponse> result = new ArrayList<>();
            YearMonth currentMonth = YearMonth.now();
            for (int i = 2; i >= 0; i--) {
                YearMonth month = currentMonth.minusMonths(i);
                LocalDate start = month.atDay(1);
                LocalDateTime startTime = start.atStartOfDay();
                LocalDateTime endTime = month.plusMonths(1).atDay(1).atStartOfDay();
                long count = repository.countByLabelAndUploadDateBetween("X", startTime, endTime);
                result.add(new DefectRateResponse(start.format(DATE_FORMAT), count));
            }
            return result;
        } catch (Exception e) {
            throw new DefectRateException(
                    "DEFECT_003",
                    "월별 불량 데이터를 집계하는 중 오류가 발생했습니다.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
