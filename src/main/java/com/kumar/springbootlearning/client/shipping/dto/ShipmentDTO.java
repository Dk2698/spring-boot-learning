package com.kumar.springbootlearning.client.shipping.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kumar.springbootlearning.client.shipping.vo.*;
import com.kumar.springbootlearning.data.model.EntityDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShipmentDTO implements EntityDTO<String> {

    private String id;
    private Integer version;

    private LocationInfo origin;

    private LocationInfo destination;

    private LocationInfo returnLocation;

    private ShipmentDetail forwardShipmentDetail;

    private ShipmentDetail reverseShipmentDetail;

    private TimeSlot expectedPickupSlot;

    private TimeSlot expectedDeliverySlot;

    /**
     * pass courier code in preferredCourier
     */
    private String preferredCourier;

    private String orgCode;

    private Boolean autoManifest;

    private String shipmentPurpose;

    private String shipperReferenceToken;

    /**
     * pass service definition code in serviceType
     */
    private String serviceType;

    private Boolean packslipRequired;

    private Boolean offloadShipment;

    private String offloadCourier;

    private LocationInfo offloadLocation;

    private String assignedCourierCode;

    private String sortCode;

    private ShippingDimensions shipmentDimension;

    private Packslip packslip;

    private Double volumetricWeight;

    private Double billedWeight;

    private Double expectedCharges;

    private TimeSlot courierPromisedPickupSlot;

    private TimeSlot courierPromisedDeliverySlot;

    private String serviceCode;
}
