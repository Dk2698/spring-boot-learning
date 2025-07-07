package com.kumar.springbootlearning.pattern.strategy.payment;

public interface PaymentStrategy {
    void pay(double amount);
    String getType(); // Used to identify the payment type
}
