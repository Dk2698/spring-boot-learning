package com.kumar.springbootlearning.channel.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShipmentEventDetail implements Serializable {

    private String orgCode;

    private String courierCode;

    private String awbNumber;

    private String shipperReferenceToken;

//    private ShipmentLocation currentLocation;

//    private ShipmentLocation nextLocation;

    private String notes;

//    private ShipmentStatus currentStatus;

//    private ShipmentStatus previousStatus;

    private String failureReason;

    private Instant timestamp;

//    private ShipmentEvent shipmentEvent;

    private Instant edd;

    private Instant pdd;

    private Map<String, String> rawEventDetails = null;

    private Boolean isSyntheticEvent;
}
