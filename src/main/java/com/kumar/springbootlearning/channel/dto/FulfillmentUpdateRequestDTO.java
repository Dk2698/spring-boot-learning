package com.kumar.springbootlearning.channel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FulfillmentUpdateRequestDTO {

//    @NotBlank
    private String fulfillmentId;
    private String orgCode;
    private String platformFulfillmentId;
//    @NotBlank
    private String eventType;
    private String notes;
    private String eventDescription;
    private String timestamp;
    private String eventSource;
    private String eventActor;

    private EventLocation eventLocation;

    private TrackingInfo trackingInfo;

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class EventLocation {
        private String locationCode;
        private String locationName;
        private String addressLine1;
        private String city;
        private String state;
        private String country;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class TrackingInfo {
        private String courierName;
        private String courierServiceName;
        private String awbNumber;
        private String trackingUrl;
        @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
        private Instant edd;
        @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
        private Instant pdd;
    }
}