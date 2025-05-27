package com.kumar.springbootlearning.valuelist.config;

import java.io.Serializable;

public interface CodeEnum extends Serializable {

    String getCode();

    default String getDescription() {
        return getCode();
    }
}