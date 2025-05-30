package com.kumar.springbootlearning.channel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumar.springbootlearning.channel.dto.ShipmentEventDetail;
import com.kumar.springbootlearning.channel.entity.Fulfillment;
import com.kumar.springbootlearning.channel.service.mapper.ChannelFulfillmentMapper;
import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class NoOpProviderService extends AbstractChannelIntegrationService {

    public NoOpProviderService(WebClient webClient, ComDelegateMapping mappings, ObjectMapper objectMapper, ChannelFulfillmentMapper channelFulfillmentMapper) {
        super(webClient, mappings, objectMapper, channelFulfillmentMapper);
    }

    @Override
    protected String getTargetService() {
        return null;
    }

    @Override
    public String getProviderName() {
        return "NoOpProvider";
    }

    @Override
    protected String getEventEndpoint() {
        return null;
    }

    @Override
    public String createFulfillment(Fulfillment fulfillment) {
        return null;
    }

    @Override
    public void updateFulfillment(Fulfillment fulfillment) {

    }

    @Override
    public void updateFulfillmentEvent(Fulfillment fulfillment, ShipmentEventDetail shipmentEventDetail) {

    }

    @Override
    public Boolean cancelFulfillment(Fulfillment fulfillment) {

        return null;
    }

    @Override
    public Boolean cancelOrder(String orgCode, String orderId) {
        return null;
    }
}
