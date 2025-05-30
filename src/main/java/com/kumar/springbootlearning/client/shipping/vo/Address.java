package com.kumar.springbootlearning.client.shipping.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Address implements Serializable {

    private String contactPerson;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String postalCode;

    private String state;

    private String country;

    private Double latitude;

    private Double longitude;

    private String addressType;

}
