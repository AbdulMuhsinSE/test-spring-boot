package com.task.discounts.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ServiceException.
 *
 * @author AbdulMuhsin J. Al-Kandari
 */
@Getter
public class ServiceException extends RuntimeException {
    private final HttpStatus statusCode;
    public ServiceException(String message) {
        super(message);
        this.statusCode = HttpStatus.BAD_REQUEST;
    }

    public ServiceException(HttpStatus status, String message){
        super(message);
        this.statusCode = status;
    }
}
