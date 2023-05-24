package com.task.discounts.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.naming.AuthenticationException;

/**
 * BaseExceptionHandler.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, HttpServletRequest response) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
