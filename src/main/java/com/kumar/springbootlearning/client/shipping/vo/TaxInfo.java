package com.kumar.springbootlearning.client.shipping.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaxInfo implements Serializable {

    private Double taxRate;

    private Double taxableAmount;

    private Double discountAmount;

    private String sellerGstin;

    private String consigneeGstin;

    private String invoiceNumber;

    private String invoiceDate;

    private Map<String, String> taxDetail;
}
