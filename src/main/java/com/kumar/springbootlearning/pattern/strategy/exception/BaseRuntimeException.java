package com.kumar.springbootlearning.pattern.strategy.exception;

public abstract class BaseRuntimeException extends RuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = -897856973823710492L;

    private final String errorKey;

    /**
     * Constructs a {@code BaseRuntimeException} with the
     * specified detail message. The string {@code reason} can be
     * retrieved later by the
     * {@link Throwable#getMessage}
     * method of class {@code java.lang.Throwable}.
     *
     * @param errorKey the key which can be used to localize the message
     * @param reason   the detail message.
     */
    protected BaseRuntimeException(String errorKey, String reason) {
        super(reason);
        this.errorKey = errorKey;
    }

    /**
     * Constructs an {@code BaseRuntimeException} with the specified detail message and cause.
     * Note that the detail message associated with cause is not automatically incorporated into this exception's detail message.
     *
     * @param errorKey the key which can be used to localize the message
     * @param message  - The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause    The cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    protected BaseRuntimeException(String errorKey, String message, Throwable cause) {
        super(message, cause);
        this.errorKey = errorKey;
    }

    public String getErrorKey() {
        return errorKey;
    }
}