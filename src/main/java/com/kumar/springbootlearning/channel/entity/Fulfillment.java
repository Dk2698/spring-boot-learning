package com.kumar.springbootlearning.channel.entity;

import com.kumar.springbootlearning.data.model.AuditableBaseEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Fulfillment extends AuditableBaseEntity<String> {

    private String fulfillmentId;
//    @NotBlank
    private String platformFulfillmentId;

    private String orgCode;

    private String channelCode;
}