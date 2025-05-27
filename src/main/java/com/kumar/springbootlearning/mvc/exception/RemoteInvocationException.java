package com.kumar.springbootlearning.mvc.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class RemoteInvocationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String ERROR_KEY = "error.remoteInvocation";

    private final String serviceName;
    private final String message;
    private final Throwable cause;
    private final String errorKey;

    public RemoteInvocationException(String errorKey, String message, String serviceName, Throwable cause) {
        super(message, cause);
        this.serviceName = serviceName;
        this.message = message;
        this.cause = cause;
        this.errorKey = errorKey;
    }

    public RemoteInvocationException(String message, String serviceName, Throwable cause) {
        this(ERROR_KEY, message, serviceName, cause);
    }
}
