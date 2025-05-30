package com.kumar.springbootlearning.client.shipping.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShipmentEventStatus implements Serializable {
    private String shipmentOrderId;
    private String shipperReferenceToken;
    private String awbNumber;
    private String currentDirection;
    private String courierCode;
    private CurrentLocation currentLocation;
    private String notes;
    private String currentStatus;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Instant lastEventTime;
    private ShipmentEvent latestShipmentEvent;
    private Event[] events;
    private long pickupAttemptedCount;
    private long deliveryAttemptedCount;
    private long returnDeliveryAttemptedCount;
    private boolean exceptionFlag;
    private boolean holdFlag;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Instant edd;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Instant pdd;
    private boolean missingFlag;
    private boolean cancellationRequested;
    private boolean deliveryAttempted;
    private boolean returnDeliveryAttempted;
    private boolean manifested;
    private boolean pickupAttempted;
    private boolean pickupCompleted;
    private boolean rtoInitiated;
    private boolean returnDelivered;
    private boolean inExceptionState;
    private boolean onHold;
    private boolean shipmentMissing;
    private boolean delivered;
    private boolean cancelled;

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
   static private class CurrentLocation {
        private String city;
        private String state;
        private String country;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    static private class Event {
        private CurrentLocation currentLocation;
        private String notes;
        private String currentStatus;
        private long timestamp;
        private String eventSource;
        private ShipmentEvent shipmentEvent;
        private String stateTransitionType;
        private boolean flagged;
        private boolean syntheticEvent;
        private boolean stateMachineProcessed;
    }
    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
   static private class ShipmentEvent {
        private String event;
        private String subEventCode;
        private String subEventCodeName;
    }
}