package com.kumar.springbootlearning.channel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumar.springbootlearning.channel.dto.*;
import com.kumar.springbootlearning.channel.entity.Fulfillment;
import com.kumar.springbootlearning.channel.exception.ChannelProcessingException;
import com.kumar.springbootlearning.channel.service.mapper.ChannelFulfillmentMapper;
import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import com.kumar.springbootlearning.mvc.exception.BadConfigurationException;
import com.kumar.springbootlearning.mvc.exception.RemoteInvocationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
public abstract class AbstractChannelIntegrationService implements ChannelIntegrationService {

    protected final WebClient webClient;

    protected final ComDelegateMapping mappings;

    protected final ObjectMapper objectMapper;

    private final ChannelFulfillmentMapper channelFulfillmentMapper;

    protected AbstractChannelIntegrationService(WebClient webClient, ComDelegateMapping mappings, ObjectMapper objectMapper, ChannelFulfillmentMapper channelFulfillmentMapper) {
        this.webClient = webClient;
        this.mappings = mappings;
        this.objectMapper = objectMapper;
        this.channelFulfillmentMapper = channelFulfillmentMapper;
    }

    @Override
    public String createFulfillment(Fulfillment entity) {
        log.info("Creating fulfillment for {} with fulfillmentId: {}", entity.getChannelCode(), entity.getFulfillmentId());
        try {
            final ChannelFulfillmentDTO dto = channelFulfillmentMapper.toDto(entity);
            final ChannelFulfillmentResponseDTO channelFulfillmentResponseDTO = sendRequest(dto, getCreateFulfillmentEndpoint(), ChannelFulfillmentResponseDTO.class);
            if (channelFulfillmentResponseDTO != null && channelFulfillmentResponseDTO.getSuccess() == Boolean.TRUE) {
                return channelFulfillmentResponseDTO.getPlatformFulfillmentId();
            }
        } catch (Exception ex) {
            throw new ChannelProcessingException(
                    entity.getChannelCode(),
                    entity.getFulfillmentId(),
                    null,  // Order ID might not be available at this stage
                    "Error while calling Channel Order Service to create fulfillment"
            );
        }
        return null; //TODO - check if we should throw exception
    }

    @Override
    public void updateFulfillmentEvent(Fulfillment fulfillment, ShipmentEventDetail shipmentEventDetail) {
        log.info("Updating fulfillment event for {} with fulfillmentId: {}", fulfillment.getChannelCode(), fulfillment.getFulfillmentId());
        try {
            FulfillmentUpdateRequestDTO fulfillmentUpdateRequestDTO = new FulfillmentUpdateRequestDTO();
            enrichFulfillmentUpdateRequestDTO(fulfillmentUpdateRequestDTO, fulfillment, shipmentEventDetail);

            if (beforeUpdateEvent(fulfillmentUpdateRequestDTO, shipmentEventDetail)) {
                EventResponseDTO eventResponseDTO = sendRequest(fulfillmentUpdateRequestDTO, getEventEndpoint(), EventResponseDTO.class);
                if (eventResponseDTO == null) {
                    throw new ChannelProcessingException(
                            fulfillment.getChannelCode(),
                            fulfillment.getFulfillmentId(),
                            null,
                            "Failed to send shipping event request"
                    );
                }
                afterUpdateEvent(eventResponseDTO, shipmentEventDetail);
            } else {
                log.debug("Shipment event ignored. No API call made.");
            }
        } catch (Exception ex) {
            throw new ChannelProcessingException(
                    fulfillment.getChannelCode(),
                    fulfillment.getFulfillmentId(),
                    null,
                    "Unexpected error while processing shipping event"
            );
        }
    }

    protected boolean beforeUpdateEvent(FulfillmentUpdateRequestDTO dto, ShipmentEventDetail entity) {
        return shouldProcessEvent(dto, entity);  // Process the event
    }

    protected void afterUpdateEvent(EventResponseDTO dto, ShipmentEventDetail entity) {
//        log.debug("Successfully processed shipment event: {}", entity.getShipmentEvent().getEvent());
    }

    protected boolean shouldProcessEvent(FulfillmentUpdateRequestDTO dto, ShipmentEventDetail entity) {
        return true;  //process the event
    }

    @Override
    public Boolean cancelFulfillment(Fulfillment fulfillment) {
        log.info("Canceling fulfillment for {} with fulfillmentId: {}", fulfillment.getChannelCode(), fulfillment.getFulfillmentId());
        ChannelCancelRequestDTO channelCancelRequestDTO = buildCancelRequestDTO(fulfillment.getOrgCode(), null, fulfillment.getPlatformFulfillmentId());
        final String endpoint = String.format("%s", getCancelFulfillmentEndpoint());
        log.debug("Initiating request to cancel fulfillment at {}", endpoint);
        try {
            ChannelFulfillmentResponseDTO channelFulfillmentResponseDTO = sendRequest(channelCancelRequestDTO, endpoint, ChannelFulfillmentResponseDTO.class);
            return  channelFulfillmentResponseDTO.getSuccess();
        } catch (Exception ex) {
            throw new ChannelProcessingException(
                    fulfillment.getChannelCode(),
                    fulfillment.getFulfillmentId(),
                    null,
                    "Unexpected error occurred during fulfillment cancellation"
            );
        }
    }

    @Override
    public Boolean cancelOrder(String orgCode, String orderId) {
        log.info("Canceling order for {} with orderId: {}", orgCode, orderId);
        ChannelCancelRequestDTO channelCancelRequestDTO = buildCancelRequestDTO(orgCode, orderId, null);

        final String uri = String.format("%s", getCancelOrderEndpoint());
        log.debug("Initiating cancel order request to URI: {}", uri);

        try {
            CancelResponse cancelResponse = sendRequest(channelCancelRequestDTO, uri, CancelResponse.class);
            return  cancelResponse.getSuccess();

        } catch (Exception ex) {
            throw new ChannelProcessingException(
                    getProviderName(),
                    null,
                    orderId,
                    "Unexpected error occurred during order cancellation"
            );
        }
    }

    @Override
    public void updateFulfillment(Fulfillment fulfillment) {
        //TODO
    }

    private ChannelCancelRequestDTO buildCancelRequestDTO(String orgCode, String orderId, String platformFulfillmentId) {
        ChannelCancelRequestDTO dto = new ChannelCancelRequestDTO();
        dto.setOrgCode(orgCode);
        if (orderId != null) {
            dto.setPlatformOrderId(orderId);
        }
        if (platformFulfillmentId != null) {
            dto.setPlatformFulfillmentId(platformFulfillmentId);
        }
        return dto;
    }

    protected void enrichFulfillmentUpdateRequestDTO(FulfillmentUpdateRequestDTO dto, Fulfillment fulfillment, ShipmentEventDetail shipmentEventDetail) {

//        dto.setFulfillmentIdentifier(fulfillment.getFulfillmentIdentifier());
        dto.setFulfillmentId(fulfillment.getFulfillmentId());
        dto.setOrgCode(fulfillment.getOrgCode());
        dto.setPlatformFulfillmentId(fulfillment.getPlatformFulfillmentId());
        dto.setTimestamp(shipmentEventDetail.getTimestamp().toString());
//        dto.setEventType(shipmentEventDetail.getShipmentEvent().getEvent());
        dto.setNotes(shipmentEventDetail.getNotes());
        dto.setEventSource("shipping-api");

        FulfillmentUpdateRequestDTO.EventLocation eventLocation = new FulfillmentUpdateRequestDTO.EventLocation();
//        if (shipmentEventDetail.getCurrentLocation() != null) {
//            ShipmentLocation location = shipmentEventDetail.getCurrentLocation();
//            if (location.getCity() != null) {
//                eventLocation.setCity(location.getCity());
//            }
//            if (location.getState() != null) {
//                eventLocation.setState(location.getState());
//            }
//            if (location.getCountry() != null) {
//                eventLocation.setCountry(location.getCountry());
//            }
//            if (location.getNode() != null) {
//                eventLocation.setLocationCode(location.getNode());
//            }
//        }

        dto.setEventLocation(eventLocation);

        FulfillmentUpdateRequestDTO.TrackingInfo trackingInfo = new FulfillmentUpdateRequestDTO.TrackingInfo();

        trackingInfo.setCourierName(shipmentEventDetail.getCourierCode());
        trackingInfo.setAwbNumber(shipmentEventDetail.getAwbNumber());
        if (shipmentEventDetail.getEdd() != null){
            trackingInfo.setEdd(shipmentEventDetail.getEdd());
        }
        if (shipmentEventDetail.getPdd() != null){
            trackingInfo.setPdd(shipmentEventDetail.getPdd());
        }
        dto.setTrackingInfo(trackingInfo);
    }

    protected <T> T sendRequest(Object requestDTO, String endpoint, Class<T> responseType) {
        final String uri = getTargetUri() + endpoint;
        log.info("Delegate call to {}", uri);
        log.debug("Request body: {}", requestDTO);

        Mono<ResponseEntity<T>> retrievedResource = webClient.post()
                .uri(uri)
                .bodyValue(requestDTO)
                .retrieve()
                .toEntity(responseType);
        try {
            final ResponseEntity<T> clientResponse = retrievedResource.block();
            if (clientResponse != null && clientResponse.getBody() != null) {
                log.debug("Response body: {}", clientResponse.getBody());
                return clientResponse.getBody();
            } else {
                throw new RemoteInvocationException("Received empty response from channel.", "Service", new Throwable("Empty response."));
            }
        } catch (Exception ex) {
            log.error("Error while calling service. ", ex);
            throw new RemoteInvocationException("Error while processing the request.", ex.getMessage(), ex);
        }
    }

    protected String getTargetUri() {
        final Map<String, String> map = mappings.getUri();
        if (map.containsKey(getTargetService())) {
            return map.get(getTargetService()) + "/api/";
        } else {
            throw new BadConfigurationException("error.missing.delegate", "Unable to find delegate", "logistiex.delegate.service.uri." + getTargetService());
        }
    }

    protected String getCreateFulfillmentEndpoint() {
        return "fulfillments/create-fulfillments";
    }

    protected String getEventEndpoint() {
        return "fulfillments/create-fulfillment-event";
    }

    protected String getCancelFulfillmentEndpoint() {
        return "fulfillments/cancel-fulfillment";
    }

    protected String getCancelOrderEndpoint() {
        return "orders/cancel-order";
    }

    protected abstract String getTargetService();

}
