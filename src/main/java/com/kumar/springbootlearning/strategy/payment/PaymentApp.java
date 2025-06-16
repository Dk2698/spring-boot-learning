package com.kumar.springbootlearning.strategy.payment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PaymentApp implements CommandLineRunner {

    @Autowired
    private PaymentContext paymentContext;

    @Override
    public void run(String... args) {
        paymentContext.pay("CREDIT_CARD", 120.0);
        paymentContext.pay("PAYPAL", 200.0);
        paymentContext.pay("UPI", 75.5);
    }
}
