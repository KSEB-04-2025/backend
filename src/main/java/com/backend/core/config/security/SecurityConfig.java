package com.backend.core.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/login",           // ✅ 로그인 허용
                                "/api/login/logout",    // ✅ 로그아웃도 허용
                                "/swagger-ui/**",       // ✅ Swagger 허용
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // ✅ 관리자만 접근 가능
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable()) // ✅ 우리가 만든 로그인 API 사용
                .logout(logout -> logout.disable()) // ✅ Spring Security 기본 로그아웃 비활성화
                .sessionManagement(session -> session
                        .maximumSessions(1)
                );

        return http.build();
    }
}
