package com.backend.core.config.swagger;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("프로젝트 API 명세서")
                        .description("AI 기반 품질 분류 시스템 API 문서입니다.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("제제원")
                                .email("zezeonekseb@gmail.com")
                                .url("")
                        )
                );
    }
}
