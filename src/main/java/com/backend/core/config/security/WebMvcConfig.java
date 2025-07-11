package com.backend.core.config.security;

import com.backend.core.interceptor.SessionCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionCheckInterceptor())
                .addPathPatterns("/api/admin/**")      // 예: /api/admin/ 이하만 보호
                .excludePathPatterns("/api/login"); // 로그인은 제외
    }
}
