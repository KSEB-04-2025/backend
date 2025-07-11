package com.backend.core.presentation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final int statusCode;
    private final String code;
    private final String message;

    public static ErrorResponse of(int statusCode, String code, String message) {
        return ErrorResponse.builder()
                .statusCode(statusCode)
                .code(code)
                .message(message)
                .build();
    }
}