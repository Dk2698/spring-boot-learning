package com.kumar.springbootlearning.pattern.strategy.payment;

import org.springframework.stereotype.Component;

@Component
public class UpiPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " via UPI");
    }

    @Override
    public String getType() {
        return "UPI";
    }
}
