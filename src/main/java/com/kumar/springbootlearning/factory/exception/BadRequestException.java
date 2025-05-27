package com.kumar.springbootlearning.factory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class BadRequestException extends ErrorResponseException {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String errorKey;

    public BadRequestException(String defaultMessage, String entityName, String errorKey) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }

    public BadRequestException(URI type, String defaultMessage, String entityName, String errorKey) {

        super(HttpStatus.BAD_REQUEST, ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, defaultMessage), null,
                "error." + errorKey, getAlertParameters(entityName, errorKey));

        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    private static String[] getAlertParameters(String entityName, String errorKey) {
        return new String[]{"error." + errorKey, entityName};
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    // private static ProblemDetail asProblemDetail(String message) {
    //     ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
    //     problemDetail.setTitle("Invalid Request");
    //     problemDetail.setType(URI.create("https://api.bookmarks.com/errors/not-found"));
    //     problemDetail.setProperty("errorCategory", "Generic");
    //     problemDetail.setProperty("timestamp", Instant.now());
    //     return problemDetail;
    // }
}