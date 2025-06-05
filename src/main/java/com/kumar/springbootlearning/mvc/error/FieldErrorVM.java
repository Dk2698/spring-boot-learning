package com.kumar.springbootlearning.mvc.error;

import com.kumar.springbootlearning.utils.StringUtils;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class FieldErrorVM implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String objectName;

    private final String field;

    private final String message;

    private final String errorCode;

    private final String key;

    public FieldErrorVM(String dto, String field, String message, String errorCode) {
        this.objectName = dto;
        this.field = StringUtils.camelToSnake(field);
        this.message = message;
        this.errorCode = errorCode;
        this.key = errorCode;
    }
}