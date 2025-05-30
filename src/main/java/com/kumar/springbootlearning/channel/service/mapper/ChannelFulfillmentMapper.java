package com.kumar.springbootlearning.channel.service.mapper;

import com.kumar.springbootlearning.channel.dto.ChannelFulfillmentDTO;
import com.kumar.springbootlearning.channel.entity.Fulfillment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelFulfillmentMapper {

    //    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "version", ignore = true)
    ChannelFulfillmentDTO toDto(Fulfillment entity);
}