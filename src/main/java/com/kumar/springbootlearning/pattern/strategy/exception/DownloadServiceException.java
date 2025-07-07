package com.kumar.springbootlearning.pattern.strategy.exception;

import lombok.Getter;

@Getter
public class DownloadServiceException extends BaseRuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = -897856973823710492L;

    private final String url;

    /**
     * Constructs a DownloadServiceException with the specified URL, error key, message, and cause.
     *
     * @param url      the URL related to the exception
     * @param errorKey the key used for localizing the error message
     * @param message  the detail message describing the exception
     * @param cause    the cause of the exception
     */
    public DownloadServiceException(String url, String errorKey, String message, Throwable cause) {
        super(errorKey, message, cause);
        this.url = url;
    }

    /**
     * Constructs a DownloadServiceException with the specified URL, error key, message, and cause.
     *
     * @param url      the URL related to the exception
     * @param errorKey the key used for localizing the error message
     * @param reason   the detail message describing the failure
     */
    public DownloadServiceException(String url, String errorKey, String reason) {
        super(errorKey, reason);
        this.url = url;
    }
}