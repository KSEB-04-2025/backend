package com.backend.auth.presentation;

import com.backend.auth.presentation.dto.LoginResponse;
import com.backend.auth.presentation.swagger.LogoutSwagger;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.Cookie;


@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LogoutController implements LogoutSwagger {
    @Override
    @PostMapping("/logout")
    public ResponseEntity<LoginResponse> logout(HttpSession session, HttpServletResponse response) {
        if (session != null) {
            try {
                session.invalidate();
            } catch (IllegalStateException e) {
                System.out.println("세션이 이미 무효화되어 있음");
            }
        }

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");            // 루트 경로로 설정해야 삭제됨
        cookie.setHttpOnly(true);      // HttpOnly 속성 유지
        cookie.setMaxAge(0);           // 즉시 만료
        response.addCookie(cookie);

        return ResponseEntity.ok(new LoginResponse("로그아웃 성공"));
    }
}
