package com.team9ookie.dangdo.config;

import com.team9ookie.dangdo.dto.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponseDto<Void>> badRequest(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(BaseResponseDto.badRequest(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseDto<Void>> badRequest(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(BaseResponseDto.internalServerError(e.getMessage()));
    }

}
