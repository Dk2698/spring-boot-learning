package com.kumar.springbootlearning.client.shipping.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShippingDimensions implements Serializable {

    /**
     * unit for length, breadth and height is cm.
     */

    private Double length;

    private Double breadth;

    private Double height;

    /**
     * unit for weight is kg.
     */

    private Double weight;

}
