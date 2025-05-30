package com.kumar.springbootlearning.client.shipping.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LocationInfo implements Serializable {

    private ContactDetails contact;
    private Address address;
    private String type;
}
