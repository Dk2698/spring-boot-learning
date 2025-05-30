package com.kumar.springbootlearning.channel.service;

import com.kumar.springbootlearning.channel.dto.ShipmentEventDetail;
import com.kumar.springbootlearning.channel.entity.Fulfillment;
import com.kumar.springbootlearning.factory.beans.ProviderService;

public interface ChannelIntegrationService extends ProviderService {

    String createFulfillment(Fulfillment fulfillment);

    void updateFulfillment(Fulfillment fulfillment);

    void updateFulfillmentEvent(Fulfillment fulfillment, ShipmentEventDetail shipmentEventDetail);

    Boolean cancelFulfillment(Fulfillment fulfillment);

    Boolean cancelOrder(String orgCode, String orderId);
}