package com.backend.auth.application;

import com.backend.auth.domain.AdminRepository;
import com.backend.auth.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public void authenticate(String username, String password) {
        var admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("ADMIN_NOT_FOUND", "존재하지 않는 관리자입니다."));

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new UnauthorizedException("INVALID_PASSWORD", "비밀번호가 올바르지 않습니다.");
        }
    }
}
