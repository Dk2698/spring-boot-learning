package com.kumar.springbootlearning.valuelist.enums;

import com.kumar.springbootlearning.valuelist.config.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum MasterLists implements BaseEnum {

    BRANDS("Brands", "brands"),
    CITIES("Cities", "cities"),
    COUNTRIES("Countries", "countries");

    private final String description;
    private final String resource;

    private static final Map<String, MasterLists> stringToEnumMap = new HashMap<>();

    static {
        for (MasterLists enumConstant : MasterLists.values()) {
            stringToEnumMap.put(enumConstant.name(), enumConstant);
        }
    }

    public static MasterLists get(String name) {
        return stringToEnumMap.get(name);
    }
}