package com.kumar.springbootlearning.factory.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumar.springbootlearning.factory.config.PaymentGatewayConfig;
import com.kumar.springbootlearning.factory.enums.PaymentGateway;
import com.kumar.springbootlearning.factory.exception.BadConfigurationException;
import com.kumar.springbootlearning.factory.request.PaymentRequestDTO;
import com.kumar.springbootlearning.factory.response.VerifyPaymentResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class PayuService implements PaymentGatewayService {

    private final WebClient webClient;

    private final ObjectMapper objectMapper;

//    private final PaymentGatewayConfig paymentGatewayConfig;

    final Map<String, String> mapping;

    public PayuService(@Qualifier("payuWebClient") WebClient webClient, ObjectMapper objectMapper, PaymentGatewayConfig paymentGatewayConfig) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        final Map<String, String> mapping = paymentGatewayConfig.getConfigsForPaymentGateway(PaymentGateway.PAYU);
        if (mapping != null) {
            this.mapping = mapping;
        }else {
            throw new BadConfigurationException("error.missing.service", "Unable to find payment service config", "logistiex.payment.service." + getProviderName().toLowerCase());
        }
    }

    @Override
    public String getProviderName() {
        return "PAYU";
    }

    @Override
    public Map<String, Object> prepareRequest(PaymentRequestDTO paymentRequest) {
        return null;
    }

    @Override
    public Map<String, Object> parseResponse(HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public URI getPaymentRequestUrl(PaymentRequestDTO paymentRequest) throws URISyntaxException {
        return null;
    }

    @Override
    public Optional<VerifyPaymentResponse> verifyPaymentRequest(String txnId) {
        return Optional.empty();
    }
}