package com.kumar.springbootlearning.mvc.exception;

import lombok.Getter;

import java.io.Serial;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
@Getter
public class ImmutablePropertyException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String fieldName;

    private final String errorKey = "error.immutableProperty";

    public ImmutablePropertyException(String defaultMessage, String entityName, String fieldName) {
        super(defaultMessage);
        this.entityName = entityName;
        this.fieldName = fieldName;
    }
}
