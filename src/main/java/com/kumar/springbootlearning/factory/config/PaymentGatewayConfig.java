package com.kumar.springbootlearning.factory.config;

import com.kumar.springbootlearning.factory.enums.PaymentGateway;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "com.kumar.payment")
public class PaymentGatewayConfig {
    /**
     * com.kumar.payment.service.payu.payuSaltKey
     */
    private Map<String, Map<String, String>> gateways;

    public Map<String, String> getConfigsForPaymentGateway(PaymentGateway paymentGateway) {
        return gateways.getOrDefault(paymentGateway.getDescription(), Collections.emptyMap());
    }
}