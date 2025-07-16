package com.backend.auth.presentation.swagger;

import com.backend.auth.presentation.dto.LoginRequest;
import com.backend.auth.presentation.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "로그인/로그아웃", description = "로그인/로그아웃 세션 기반 인증 API")
public interface LoginSwagger {

    @Operation(
            summary = "로그인",
            description = "username, password로 로그인. 성공 시 세션이 생성되고 쿠키(JSESSIONID)가 내려갑니다."
    )
    ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request,
            jakarta.servlet.http.HttpSession session
    );

}
