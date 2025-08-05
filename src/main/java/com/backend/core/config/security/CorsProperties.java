package com.backend.core.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "cors.allowed")
public class CorsProperties {
    private List<String> paths;
    private List<String> origins;
    private List<String> methods;
    private List<String> headers;
    private List<String> exposedHeaders;
    private Long maxAge;
    private Boolean credentials;
}
