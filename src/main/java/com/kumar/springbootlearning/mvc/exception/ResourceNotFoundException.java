package com.kumar.springbootlearning.mvc.exception;

public class ResourceNotFoundException extends BaseCustomException {
    public ResourceNotFoundException(String message, String details, String s) {
        super(message, "RESOURCE_NOT_FOUND", details);
    }
}
