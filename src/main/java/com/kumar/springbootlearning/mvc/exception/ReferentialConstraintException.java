package com.kumar.springbootlearning.mvc.exception;

import lombok.Getter;

import java.io.Serial;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
@Getter
public class ReferentialConstraintException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String fieldName;

    private final String errorKey = "error.referentialConstraint";

    public ReferentialConstraintException(String defaultMessage, String entityName, String fieldName) {
        super(defaultMessage);
        this.entityName = entityName;
        this.fieldName = fieldName;
    }
}
