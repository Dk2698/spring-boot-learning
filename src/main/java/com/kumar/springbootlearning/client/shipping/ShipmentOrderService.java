package com.kumar.springbootlearning.client.shipping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumar.springbootlearning.client.shipping.dto.ShipmentDTO;
import com.kumar.springbootlearning.client.shipping.exception.CourierAssignmentException;
import com.kumar.springbootlearning.client.shipping.request.ShipmentControlRequest;
import com.kumar.springbootlearning.client.shipping.response.ShipmentControlResponse;
import com.kumar.springbootlearning.client.shipping.vo.ShipmentEventStatus;
import com.kumar.springbootlearning.factory.exception.BadRequestException;
import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import com.kumar.springbootlearning.mvc.exception.RemoteInvocationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ShipmentOrderService {

    private final WebClient webClient;

    private final ComDelegateMapping mappings;

    private final ObjectMapper objectMapper;

    @Value("${com.shipping.end-point}")
    private String shippingEndPoint;


    public ShipmentOrderService(@Qualifier("shippingWebClient") WebClient webClient, ComDelegateMapping mappings, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.mappings = mappings;
        this.objectMapper = objectMapper;
    }

    public String getTargetUri() {
        final Map<String, String> map = mappings.getUri();
        if (map.containsKey("shipping")) {
            return map.get("shipping") + "/api/";
        } else {
            throw new BadRequestException("Unable to find shipping", "shipping", "shippingnotfound");
        }
    }

    public ShipmentDTO assignCourier(ShipmentDTO dto) {
        final String uri = getTargetUri() + shippingEndPoint;
        Mono<ShipmentDTO> retrievedResource = webClient.post()
                .uri(uri)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ShipmentDTO.class);

        try {
            final ShipmentDTO clientResponse = retrievedResource.block();
            if (clientResponse != null) {
                return clientResponse;
            } else {
                log.error("Invalid input for Shipment Order Service.");
                return null;            }
        } catch (Exception ex) {
            String message = extractCourierErrorMessage(ex);
            throw new CourierAssignmentException(message, ex);
        }
    }

    private String extractCourierErrorMessage(Exception ex) {
        String jsonResponse = ex.getMessage();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            return rootNode.path("message").asText();
        } catch (Exception e) {
            log.error("Failed to parse error message from JSON", e);
            return "Unknown error occurred";
        }
    }

    public ShipmentDTO manifestShipmentOrder(String shipmentOrderId) {
        final String uri = getTargetUri() + shippingEndPoint + "/" + shipmentOrderId + "/manifest";
        Mono<ShipmentDTO> retrievedResource = webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(ShipmentDTO.class);

        try {
            final ShipmentDTO clientResponse = retrievedResource.block();
            if (clientResponse != null) {
                return clientResponse;
            } else {
                throw new BadRequestException("Invalid input ", "Not Found response.", "invalid_input");
            }
        } catch (Exception ex) {
            log.error("Error while calling Shipment Order Manifest Service. ", ex);
            String message = extractCourierErrorMessage(ex);
            throw new CourierAssignmentException(message, ex);
        }
    }

//    public UpdateShipmentResponseDTO updateShipment(UpdateShipmentDTO dto) {
//        final String uri = getTargetUri() + shippingEndPoint + "/" + dto.getShipmentOrderId() + "/shipmentcontrolevents";
//        Mono<UpdateShipmentResponseDTO> retrievedResource = webClient.post()
//                .uri(uri)
//                .bodyValue(dto)
//                .retrieve()
//                .bodyToMono(UpdateShipmentResponseDTO.class);
//
//        try {
//            final UpdateShipmentResponseDTO clientResponse = retrievedResource.block();
//            if (clientResponse != null) {
//                return clientResponse;
//            } else {
//                log.error("Invalid input for Shipment Order Service.");
//                return null;
//            }
//        } catch (Exception ex) {
//            log.error("Error while calling Shipment Order Service. ", ex);
//            return null;
//        }
//    }

    public List<ShipmentEventStatus> getShipmentEventStatus(String shipmentOrderId) {
        final String uri = getTargetUri() + shippingEndPoint + "/" + shipmentOrderId + "/v2/status";
        Mono<ResponseEntity<List<ShipmentEventStatus>>> retrievedResource = webClient.get()
                .uri(uri)
                .retrieve()
                .toEntityList(ShipmentEventStatus.class);
        try {
            final ResponseEntity<List<ShipmentEventStatus>> clientResponse = retrievedResource.block();
            if (clientResponse != null) {
                return clientResponse.getBody();
            } else {
                throw new BadRequestException("Invalid shipmentOrderId ", "Not Found response.", "invalid_shipment_order_id");
            }
        } catch (Exception ex) {
            log.error("Error while calling Shipment Order Manifest Service. ", ex);
            throw new BadRequestException("Invalid shipmentOrderId ", "Not Found response.", "invalid_shipment_order_id");
        }
    }

    public ShipmentControlResponse cancelShipmentOrder(String shipmentOrderId, ShipmentControlRequest shipmentcontrolRequest) {
        final String uri = String.format("%s%s/%s/shipmentcontrolevents", getTargetUri(), shippingEndPoint, shipmentOrderId);

        try {
            ShipmentControlResponse clientResponse = webClient.post()
                    .uri(uri)
                    .bodyValue(shipmentcontrolRequest)
                    .retrieve()
                    .bodyToMono(ShipmentControlResponse.class)
                    .block();

            if (clientResponse == null) {
                String errorMessage = "Received null response from Shipment Order Service.";
                log.error(errorMessage);
                throw new RemoteInvocationException("Invalid input. ", "Shipment Order Service.", new Throwable(errorMessage));
            }

            return clientResponse;
        } catch (WebClientResponseException ex) {
            String errorMessage = String.format("Error while calling Cancel Shipment Order Service. Status: %d, Body: %s", ex.getStatusCode().value(), ex.getResponseBodyAsString());
            log.error(errorMessage, ex);
            throw new RemoteInvocationException("Error while calling Shipment Order Service: ", errorMessage, ex);
        } catch (Exception ex) {
            log.error("Unexpected error while calling Cancel Shipment Order Service.", ex);
            throw new RemoteInvocationException("Error while calling Shipment Order Service: ", ex.getMessage(), ex);
        }
    }
}