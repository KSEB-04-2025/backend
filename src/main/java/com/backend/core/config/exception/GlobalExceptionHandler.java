package com.backend.core.config.exception;

import com.backend.auth.exception.UnauthorizedException;
import com.backend.core.presentation.ErrorResponse;
import com.backend.dashboard.exception.DashboardException;
import com.backend.product.exception.ProductListException;
import com.backend.product.exception.ProductQualityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 인증 실패(로그인 안함, 세션 없음 등)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
        log.warn("인증 오류: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of(401, "UNAUTHORIZED", ex.getMessage()));
    }

    // 유효성 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String field = ex.getBindingResult().getFieldError().getField();
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        log.warn("유효성 검증 실패: {} - {}", field, message);
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of(400, "VALIDATION_ERROR", field + ": " + message));
    }

    // 예상 못 한 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("알 수 없는 오류", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(500, "INTERNAL_ERROR", "예상치 못한 오류가 발생했습니다."));
    }

    @ExceptionHandler(DashboardException.class)
    public ResponseEntity<ErrorResponse> handleDashboard(DashboardException ex) {
        log.warn("대시보드 오류: {} [{}]", ex.getMessage(), ex.getCode());
        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorResponse.of(ex.getStatus().value(), ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(ProductQualityException.class)
    public ResponseEntity<ErrorResponse> handleProductQuality(ProductQualityException ex) {
        log.warn("제품 품질 오류: {} [{}]", ex.getMessage(), ex.getCode());
        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorResponse.of(ex.getStatus().value(), ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(ProductListException.class)
    public ResponseEntity<ErrorResponse> handleProductList(ProductListException ex) {
        log.warn("제품 목록 오류: {} [{}]", ex.getMessage(), ex.getCode());
        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorResponse.of(ex.getStatus().value(), ex.getCode(), ex.getMessage()));
    }


}

