package com.backend.dashboard.presentation;

import com.backend.dashboard.application.UniformityService;
import com.backend.dashboard.presentation.dto.UniformityResponse;
import com.backend.dashboard.presentation.swagger.UniformitySwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UniformityController implements UniformitySwagger {

    private final UniformityService uniformityService;

    @Override
    public ResponseEntity<List<UniformityResponse>> getUniformityList() {
        List<UniformityResponse> response = uniformityService.getUniformityList();
        return ResponseEntity.ok(response);
    }
}