package com.backend.dashboard.exception;

public class DashboardException extends RuntimeException {
    public DashboardException(String message) {
        super(message);
    }
    // 필요하다면 생성자/필드 추가 가능 (예: code, 원인 등)
}
