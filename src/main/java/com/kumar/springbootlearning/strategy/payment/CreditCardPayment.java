package com.kumar.springbootlearning.strategy.payment;

import org.springframework.stereotype.Component;

@Component
public class CreditCardPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " with Credit Card");
    }

    @Override
    public String getType() {
        return "CREDIT_CARD";
    }
}
