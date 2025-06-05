package com.kumar.springbootlearning.mvc.exception;

import lombok.Getter;

@Getter
public class ValidationException extends BaseRuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = -897856973823710492L;

    private final String attributeName;

    /**
     * Constructs a {@code ValidationException} with the
     * specified detail message. The string {@code reason} can be
     * retrieved later by the
     * {@link Throwable#getMessage}
     * method of class {@code java.lang.Throwable}.
     *
     * @param attributeName Name of the attribute which is not valid
     * @param errorKey      the key which can be used to localize the message
     * @param reason        the detail message.
     */
    public ValidationException(String attributeName, String errorKey, String reason) {
        super(errorKey, reason);
        this.attributeName = attributeName;
    }

    /**
     * Constructs an {@code ValidationException} with the specified detail message and cause.
     * Note that the detail message associated with cause is not automatically incorporated into this exception's detail message.
     *
     * @param attributeName Name of the attribute which is not valid
     * @param errorKey      the key which can be used to localize the message
     * @param message       - The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause         The cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ValidationException(String attributeName, String errorKey, String message, Throwable cause) {
        super(errorKey, message, cause);
        this.attributeName = attributeName;
    }
}
