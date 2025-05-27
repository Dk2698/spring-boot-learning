package com.kumar.springbootlearning.valuelist.enums;

import com.kumar.springbootlearning.valuelist.config.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SpanLevel implements CodeEnum {
    FACILITY("1", "Facility"), CLUSTER("2", "Cluster"), REGION("3", "Region"), ZONE("4", "Zone"),
    NATIONAL("5", "National");

    private final String code;
    private final String description;
}
