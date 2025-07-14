package com.backend.auth.presentation;

import com.backend.auth.domain.Admin;
import com.backend.auth.domain.AdminRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/util")
@Tag(name = "유틸", description = "비밀번호 해시 생성 및 DB 저장 유틸리티 API")
public class UtilController {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    public UtilController(PasswordEncoder passwordEncoder, AdminRepository adminRepository) {
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
    }

    @Operation(
            summary = "admin 비밀번호 변경",
            description = "입력한 비밀번호를 bcrypt 해시로 암호화해서 admin 계정의 비밀번호를 변경합니다."
    )
    @PostMapping("/admin-password")
    public String updateAdminPassword(@RequestParam String password) {
        String hashed = passwordEncoder.encode(password);
        Admin admin = adminRepository.findByUsername("admin")
                .orElse(null);

        if (admin == null) {
            // admin 계정이 없으면 새로 생성
            admin = new Admin(null, "admin", hashed);
        } else {
            admin = new Admin(admin.getId(), "admin", hashed);
        }
        adminRepository.save(admin);
        return "admin 계정 비밀번호가 성공적으로 변경(또는 신규 생성)되었습니다.";
    }

}
