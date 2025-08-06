package com.backend.core.config.swagger;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("https://zezeone-sf.site"))
                .addServersItem(new Server().url("http://localhost:8080").description("로컬 개발 서버"))
                .info(new Info()
                        .title("프로젝트 API 명세서")
                        .description("AI 기반 결함 탐지 및 품질 분류 시스템 API 문서입니다.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("제제원")
                                .email("zezeonekseb@gmail.com")
                                .url("")
                        )
                );
    }
}
