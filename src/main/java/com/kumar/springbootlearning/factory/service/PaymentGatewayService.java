package com.kumar.springbootlearning.factory.service;

import com.kumar.springbootlearning.factory.beans.ProviderService;
import com.kumar.springbootlearning.factory.request.PaymentRequestDTO;
import com.kumar.springbootlearning.factory.response.VerifyPaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

public interface PaymentGatewayService extends ProviderService {

    Map<String, Object> prepareRequest(PaymentRequestDTO paymentRequest);

    Map<String, Object> parseResponse(HttpServletRequest httpServletRequest);

    URI getPaymentRequestUrl(PaymentRequestDTO paymentRequest) throws URISyntaxException;

    Optional<VerifyPaymentResponse> verifyPaymentRequest(String txnId);
}