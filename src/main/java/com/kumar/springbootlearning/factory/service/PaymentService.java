package com.kumar.springbootlearning.factory.service;

import com.kumar.springbootlearning.factory.config.PaymentGatewayRegistry;
import com.kumar.springbootlearning.factory.enums.PaymentGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {

    private final PaymentGatewayRegistry paymentGatewayRegistry;

    public PaymentService(PaymentGatewayRegistry paymentGatewayRegistry) {
        this.paymentGatewayRegistry = paymentGatewayRegistry;
    }

    public  void processPayment(){
        final PaymentGateway paymentGateway = getPaymentGateway();
        final PaymentGatewayService paymentService = getPaymentGatewayService(paymentGateway);
        log.debug(paymentService.getProviderName());
    }
    private PaymentGateway getPaymentGateway() {
        return PaymentGateway.PAYU;
    }

    public PaymentGatewayService getPaymentGatewayService(PaymentGateway paymentGateway) {
        return paymentGatewayRegistry.getProvider(paymentGateway.name());
    }
}