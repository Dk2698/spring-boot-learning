package com.kumar.springbootlearning.channel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumar.springbootlearning.channel.service.mapper.ChannelFulfillmentMapper;
import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AmazonIntegrationService extends AbstractChannelIntegrationService {

    protected AmazonIntegrationService(@Qualifier("channelWebClient") WebClient webClient, ComDelegateMapping mappings, ObjectMapper objectMapper, ChannelFulfillmentMapper channelFulfillmentMapper) {
        super(webClient, mappings, objectMapper, channelFulfillmentMapper);
    }

    @Override
    public String getTargetService() {
        return "amazon";
    }

    @Override
    public String getProviderName() {
        return "AMAZON";
    }
}