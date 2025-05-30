package com.kumar.springbootlearning.channel.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kumar.springbootlearning.data.model.EntityDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FulfillmentDTO implements EntityDTO<String> {
    private String id;
    private Integer version;

    private String fulfillmentId;
//    @NotBlank
    private String platformFulfillmentId;

    private String orgCode;

    private String channelCode;

}
