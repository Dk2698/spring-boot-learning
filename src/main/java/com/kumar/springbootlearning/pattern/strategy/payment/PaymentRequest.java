package com.kumar.springbootlearning.pattern.strategy.payment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequest {
    // Getters and setters
    @Getter
    private String type;
    private double amount;

    public void setType(String type) {
        this.type = type.toUpperCase(); // Normalize to match strategy key
    }
}
