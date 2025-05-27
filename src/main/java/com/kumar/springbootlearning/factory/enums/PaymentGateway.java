package com.kumar.springbootlearning.factory.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentGateway implements BaseEnum {

    PAYU("payu", "PU"),
    JUSPAY("JUSPAY", "JP");

    private final String description;
    private String prefix;
}