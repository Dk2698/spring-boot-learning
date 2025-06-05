package com.kumar.springbootlearning.mvc.exception;

import java.io.Serial;
import java.lang.annotation.Annotation;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class ConstraintDefinitionException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String errorKey;
    private final Annotation attribute;

    public ConstraintDefinitionException(String defaultMessage, Annotation attribute, String errorKey) {
        super(defaultMessage);
        this.attribute = attribute;
        this.errorKey = errorKey;
    }

    public Annotation getAttribute() {
        return attribute;
    }

    public String getErrorKey() {
        return errorKey;
    }
}
