package com.kumar.springbootlearning.strategy.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentContext {

    private final Map<String, PaymentStrategy> strategyMap = new HashMap<>();

    @Autowired
    public PaymentContext(List<PaymentStrategy> strategies) {
        for (PaymentStrategy strategy : strategies) {
            strategyMap.put(strategy.getType(), strategy);
        }
    }

    public void pay(String type, double amount) {
        PaymentStrategy strategy = strategyMap.get(type.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment type: " + type);
        }
        strategy.pay(amount);
    }
}
