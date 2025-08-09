package com.backend.auth.presentation;

import com.backend.auth.application.LoginService;
import com.backend.auth.presentation.dto.LoginRequest;
import com.backend.auth.presentation.dto.LoginResponse;
import com.backend.auth.presentation.swagger.LoginSwagger;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        loginService.authenticate(request.getUsername(), request.getPassword());

        session.setAttribute("isAdmin", true);
        session.setAttribute("loginId", request.getUsername());

        Authentication auth = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return ResponseEntity.ok(new LoginResponse("로그인 성공"));
    }
}
