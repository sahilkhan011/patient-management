package com.sk.patientservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 404 - Patient not found
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            PatientNotFoundException ex) {

        Map<String, Object> res = new HashMap<>();
        res.put("status", HttpStatus.NOT_FOUND.value());
        res.put("error", "Not Found");
        res.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(res);
    }

    // 400 - Validation error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        Map<String, Object> res = new HashMap<>();
        res.put("status", HttpStatus.BAD_REQUEST.value());
        res.put("error", "Bad Request");
        res.put("message", errorMessage);

        return ResponseEntity
                .badRequest()
                .body(res);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> emailExistErrorHandler(
            EmailAlreadyExistException ex) {

        log.warn("Email Already Exist: {}",ex.getMessage());

        Map<String, Object> res = new HashMap<>();
        res.put("status", HttpStatus.CONFLICT.value());
        res.put("error", "Email Conflict");
        res.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }
}
