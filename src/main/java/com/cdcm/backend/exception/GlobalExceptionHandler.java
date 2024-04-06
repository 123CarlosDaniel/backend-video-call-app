package com.cdcm.backend.exception;

import com.cdcm.backend.exception.customs.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex) {
        Map<String, String> errMap = new HashMap<>();
        errMap.put("ErrorMessage", ex.getMessage());
        return new ResponseEntity<>(errMap, ex.getHttpStatus());
    }
}
