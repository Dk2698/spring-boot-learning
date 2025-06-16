package com.kumar.springbootlearning.strategy.payment;

import org.springframework.stereotype.Component;

@Component
public class PayPalPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " via PayPal");
    }

    @Override
    public String getType() {
        return "PAYPAL";
    }
}
