package com.kumar.springbootlearning.client.shipping.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kumar.springbootlearning.client.shipping.vo.Address;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShipmentControlResponse implements Serializable {
    private String id;
    private List<String> shipmentInstructions;
    private String notes;

    private String alternateContactPerson;
    private String alternateContactNumber;
    private Address alternateDeliveryAddress;
    private Address alternatePickupAddress;
    private Boolean escalated;
    private Double cashToBeCollected;
}