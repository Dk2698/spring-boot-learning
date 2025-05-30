package com.kumar.springbootlearning.client.shipping.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShipmentDetail implements Serializable {

    private String consignorGstin;

    private Double shipmentValue;

    private ShippingDimensions dimensions;

    private Boolean essential;

    private Boolean fragile;

    private Boolean dangerous;

    private Integer itemCount;

    private Integer boxCount;

    private List<String> awbNumbers;

    private List<String> packagingSerialNumbers;

    private String packageScanAction;

    private PaymentSummary paymentInfo;

    private String ewayBillNumber;

    private Map<String, String> additionalAttributes;

    private List<ItemInfo> items;

    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

}
