package com.kumar.springbootlearning.mvc.exception;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String errorKey;

    public RecordNotFoundException(String defaultMessage, String entityName, String errorKey) {
        super(defaultMessage);
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
}
