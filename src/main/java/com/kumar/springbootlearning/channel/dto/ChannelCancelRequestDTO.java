package com.kumar.springbootlearning.channel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChannelCancelRequestDTO {
    private String orgCode;
    @JsonProperty("order_id")
    private String platformOrderId;
    @JsonProperty("fulfillment_id")
    private String platformFulfillmentId;
}
