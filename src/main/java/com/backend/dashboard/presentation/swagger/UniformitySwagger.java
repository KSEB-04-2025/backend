package com.backend.dashboard.presentation.swagger;

import com.backend.dashboard.presentation.dto.UniformityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "대시보드", description = "제품별 균일도/클러스터/등급 목록 반환 API")
@RequestMapping("/api/admin/dashboard/uniformity")
public interface UniformitySwagger {

    @Operation(
            summary = "균일도 전체 조회",
            description = " A, B 등급의 모든 데이터의 id, label, n_clusters, uniformity 반환"
    )
    @GetMapping
    ResponseEntity<List<UniformityResponse>> getUniformityList();
}