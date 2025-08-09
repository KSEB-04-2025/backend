package com.backend.auth.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {

    private final String code;
    private final HttpStatus status;

    public UnauthorizedException(String code, String message) {
        super(message);
        this.code = code;
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public UnauthorizedException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
