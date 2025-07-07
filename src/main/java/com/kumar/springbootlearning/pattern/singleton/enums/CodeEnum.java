package com.kumar.springbootlearning.pattern.singleton.enums;

import java.io.Serializable;

public interface CodeEnum extends Serializable {
    String getCode();

    default String getDescription() {
        return getCode();
    }
}