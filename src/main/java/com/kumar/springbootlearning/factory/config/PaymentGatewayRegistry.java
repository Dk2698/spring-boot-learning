package com.kumar.springbootlearning.factory.config;

import com.kumar.springbootlearning.factory.service.PaymentGatewayService;
import com.kumar.springbootlearning.factory.beans.ProviderBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayRegistry extends ProviderBeanFactory<PaymentGatewayService> {
    public PaymentGatewayRegistry(ApplicationContext context) {
        super(context);
    }
}