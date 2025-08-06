package com.backend.dashboard.application;

import com.backend.dashboard.domain.ProductQualityDashboardRepository;
import com.backend.dashboard.exception.UniformityException;
import com.backend.dashboard.presentation.dto.UniformityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniformityService {
    private final ProductQualityDashboardRepository repository;

    public List<UniformityResponse> getUniformityList() {
        List<UniformityResponse> result = repository.findByLabelNot("X")
                .stream()
                .map(pq -> UniformityResponse.builder()
                        .id(pq.getId())
                        .label(pq.getLabel())
                        .nClusters(pq.getNClusters())
                        .uniformity(pq.getUniformity())
                        .build())
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new UniformityException(
                    "U001",
                    "label이 X가 아닌 데이터가 존재하지 않습니다.",
                    HttpStatus.NOT_FOUND
            );
        }
        return result;
    }
}
