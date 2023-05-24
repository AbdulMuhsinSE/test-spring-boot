package com.task.discounts.authentication.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * AuthEntryPointJwt.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@Component
@Slf4j
@AllArgsConstructor
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        log.error("Unauthorized: {}", authException.getMessage());
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}
