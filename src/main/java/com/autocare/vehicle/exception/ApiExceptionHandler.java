package com.autocare.vehicle.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponse> nf(ResourceNotFoundException e, HttpServletRequest r) {
        return body(HttpStatus.NOT_FOUND, e.getMessage(), r);
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    ResponseEntity<ErrorResponse> bad(Exception e, HttpServletRequest r) {
        String m = e instanceof MethodArgumentNotValidException x ? x.getBindingResult().getFieldErrors().stream().findFirst().map(f -> f.getField() + ": " + f.getDefaultMessage()).orElse("Invalid request") : e.getMessage();
        return body(HttpStatus.BAD_REQUEST, m, r);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, DuplicateResourceException.class})
    ResponseEntity<ErrorResponse> dup(Exception e, HttpServletRequest r) {
        return body(HttpStatus.CONFLICT, "Vehicle registration already exists", r);
    }

    ResponseEntity<ErrorResponse> body(HttpStatus s, String m, HttpServletRequest r) {
        return ResponseEntity.status(s).body(new ErrorResponse(LocalDateTime.now(), s.value(), s.getReasonPhrase(), m, r.getRequestURI()));
    }

    public record ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
    }
}
