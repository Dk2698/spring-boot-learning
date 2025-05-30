package com.kumar.springbootlearning.channel.dto;

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
public class ChannelFulfillmentResponseDTO {
    private Boolean success;
    /**
     * if success true then return  shopifyFulfillmentId
     */
    private String platformFulfillmentId;
    private String message;
}
