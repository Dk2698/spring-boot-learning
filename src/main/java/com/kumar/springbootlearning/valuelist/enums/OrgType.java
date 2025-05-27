package com.kumar.springbootlearning.valuelist.enums;

import com.kumar.springbootlearning.valuelist.config.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum OrgType implements BaseEnum {

    SERVICE_PROVIDER("Service Provider", "LSP"), COURIER("Courier", "3PL"), MARKETPLACE("Marketplace", "MP"),
    SELLER("Seller", "SL"), BRANDS("Brands", "BR"), CONSIGNOR("Consignor", "CNR"), CONSIGNEE("Consignee", "CNE"),
    SYSTEM("System", "SYS");

    private final String description;
    private final String prefix;
    private static final Map<String, OrgType> stringToEnumMap = new HashMap<>();

    static {
        for (OrgType enumConstant : OrgType.values()) {
            stringToEnumMap.put(enumConstant.name(), enumConstant);
        }
    }

    public static OrgType get(String name) {
        return stringToEnumMap.get(name);
    }
}