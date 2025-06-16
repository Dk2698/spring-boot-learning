package com.kumar.springbootlearning.strategy.payment;

public interface PaymentStrategy {
    void pay(double amount);
    String getType(); // Used to identify the payment type
}
