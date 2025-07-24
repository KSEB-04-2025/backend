package com.backend.dashboard.application;

import com.backend.dashboard.domain.ProductQuality;
import com.backend.dashboard.domain.ProductQualityRepository;
import com.backend.dashboard.presentation.dto.DashboardSummaryResponse;
import com.backend.dashboard.presentation.dto.QualityTrendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ProductQualityRepository productQualityRepository;

    public DashboardSummaryResponse getSummary() {
        long total = productQualityRepository.count();
        long aCount = productQualityRepository.countByLabel("A");
        long bCount = productQualityRepository.countByLabel("B");
        return new DashboardSummaryResponse(total, aCount, bCount);
    }
    public List<QualityTrendResponse> getQualityTrends() {
        LocalDateTime weekAgo = LocalDate.now().minusDays(6).atStartOfDay();
        List<ProductQuality> list = productQualityRepository.findByUploadDateAfter(weekAgo);

        Map<String, long[]> map = new TreeMap<>();
        for (ProductQuality pq : list) {
            String day = pq.getUploadDate().toLocalDate().toString(); // yyyy-MM-dd
            map.putIfAbsent(day, new long[2]);
            if ("A".equalsIgnoreCase(pq.getLabel())) map.get(day)[0]++;
            if ("B".equalsIgnoreCase(pq.getLabel())) map.get(day)[1]++;
        }

        // 7일간 날짜별 0채우기
        List<QualityTrendResponse> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            String dateStr = date.toString();
            long[] cnt = map.getOrDefault(dateStr, new long[]{0, 0});
            result.add(new QualityTrendResponse(dateStr, cnt[0], cnt[1]));
        }
        return result;
    }
}