package com.backend.auth.application;

import com.backend.auth.domain.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AdminRepository adminRepository;

    public boolean authenticate(String username, String password) {
        return adminRepository.findByUsername(username)
                .map(admin -> admin.getPassword().equals(password)) // 실무는 해싱 체크!
                .orElse(false);
    }
}
