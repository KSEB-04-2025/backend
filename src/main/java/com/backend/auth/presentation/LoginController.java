package com.backend.auth.presentation;


import com.backend.auth.application.LoginService;
import com.backend.auth.presentation.dto.LoginRequest;
import com.backend.auth.presentation.dto.LoginResponse;
import com.backend.auth.presentation.swagger.LoginSwagger;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController implements LoginSwagger {

    private final LoginService loginService;

    @Override
    @PostMapping
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request,
            HttpSession session
    ) {
        boolean success = loginService.authenticate(request.getUsername(), request.getPassword());
        if (success) {
            session.setAttribute("isAdmin", true);
            session.setAttribute("loginId", request.getUsername());
            return ResponseEntity.ok(new LoginResponse("로그인 성공"));
        }
        return ResponseEntity.status(401).body(new LoginResponse("로그인 실패"));
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<LoginResponse> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(new LoginResponse("로그아웃 성공"));
    }
}
