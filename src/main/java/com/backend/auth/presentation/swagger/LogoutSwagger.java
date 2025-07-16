package com.backend.auth.presentation.swagger;

import com.backend.auth.presentation.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

@Tag(name = "로그인/로그아웃", description = "로그아웃 세션 기반 인증 API")
public interface LogoutSwagger {

    @Operation(
            summary = "로그아웃",
            description = "세션을 만료(삭제)해서 로그아웃 처리합니다."
    )
    ResponseEntity<LoginResponse> logout(
            HttpSession session,
            HttpServletResponse response
    );
}
