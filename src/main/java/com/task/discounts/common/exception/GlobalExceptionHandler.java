package com.task.discounts.common.exception;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.naming.AuthenticationException;
import java.net.BindException;

/**
 * GlobalExceptionHandler.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({AuthenticationException.class, JwtException.class})
    public ResponseEntity<String> handleAuthException(AuthenticationException ex, HttpServletRequest response) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleValidationException(BindException ex, HttpServletRequest response) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceException(ServiceException ex, HttpServletRequest response) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }
}
