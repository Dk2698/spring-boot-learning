package com.kumar.springbootlearning.channel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumar.springbootlearning.channel.dto.FulfillmentUpdateRequestDTO;
import com.kumar.springbootlearning.channel.dto.ShipmentEventDetail;
import com.kumar.springbootlearning.channel.service.mapper.ChannelFulfillmentMapper;
import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ShopifyIntegrationService extends AbstractChannelIntegrationService {

    public ShopifyIntegrationService(@Qualifier("channelWebClient") WebClient webClient, ComDelegateMapping mappings, ObjectMapper objectMapper, ChannelFulfillmentMapper channelFulfillmentMapper) {
        super(webClient, mappings, objectMapper, channelFulfillmentMapper);
    }

    @Override
    public String getTargetService() {
        return "shopify";
    }

    @Override
    public String getProviderName() {
        return "SHOPIFY";
    }

    @Override
    protected boolean shouldProcessEvent(FulfillmentUpdateRequestDTO dto, ShipmentEventDetail entity) {
        List<String> eventList = List.of(
                "SoftDataUploadedEvent",
                "ShipmentRequestedEvent",
                "PickupCompletedEvent",
                "ShipmentInscanEvent",
                "DeliveryFailedEvent",
                "OutForDeliveryEvent",
                "DeliveryAttemptedEvent",
                "DeliveryCompletedEvent");
//        return eventList.contains(entity.getShipmentEvent().getEvent());
        return eventList.contains(entity.getAwbNumber());
    }
}
