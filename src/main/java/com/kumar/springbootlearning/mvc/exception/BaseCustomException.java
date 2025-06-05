package com.kumar.springbootlearning.mvc.exception;

import java.time.LocalDateTime;

public abstract class BaseCustomException extends RuntimeException {
    private final String errorCode;
    private final String details;
    private final LocalDateTime timestamp;

    public BaseCustomException(String message, String errorCode, String details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public String getErrorCode() { return errorCode; }
    public String getDetails() { return details; }
    public LocalDateTime getTimestamp() { return timestamp; }
}