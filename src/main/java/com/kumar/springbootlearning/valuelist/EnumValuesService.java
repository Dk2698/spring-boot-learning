package com.kumar.springbootlearning.valuelist;

import com.kumar.springbootlearning.valuelist.config.ComValueListMapping;
import com.kumar.springbootlearning.valuelist.enums.MasterLists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class EnumValuesService {

    private final ComValueListMapping mappings;

    public EnumValuesService(ComValueListMapping mappings) {
        this.mappings = mappings;
    }

    public List<String> getMasterListValues(EnumListInfo masterList) {
        log.debug("Get possible values for {} ", masterList.value());
        return new ArrayList<>();
    }

    public List<String> getEnumValues(String enumKey) {
        List<String> list = new ArrayList<>();

        if (mappings != null) {
            final String enumClassName = mappings.getMappings().get(enumKey);

            try {
                final Class<?> enumClass = Class.forName(enumClassName);

                if (enumClass.isEnum()) {
                    final Enum<?>[] enumConstants = (Enum<?>[]) enumClass.getEnumConstants();
                    list = Arrays.stream(enumConstants).map(Enum::name).toList();
                } else {
                    // Log an error for an invalid configuration
                    log.error("Invalid configuration for EnumList {}. It must be a valid Enum.", enumKey);
                }
            } catch (ClassNotFoundException e) {
                // Log an error for a ClassNotFoundException
                log.error("Invalid configuration for EnumList {}. It must be a valid Enum. Exception - {}", enumKey, e.getMessage());
            }
        } else {
            // Log an error for no mappings found
            log.error("Invalid configuration for EnumList {}. No entry found in configuration for mappings.{} ", enumKey, enumKey);
        }

        return list;
    }

    public EnumListInfo getEnumListInfo(String enumKey) {
        log.debug("REST request to get Enum Key {}", enumKey);

        if (mappings.getMappings() != null) {
            final String mapping = mappings.getMappings().get(enumKey);
            if (mapping != null) {
                return new EnumListInfo(enumKey, "value-list");
            }
        } else {
            log.warn("No Enum/ValueList configured");
        }
        final MasterLists masterList = MasterLists.get(enumKey);
        if (masterList != null) {
            return new EnumListInfo(masterList.name(), masterList.getResource());
        }
        log.warn("No configuration found for Enum/MasterList - {}", enumKey);
        return null;
    }

    public List<EnumListInfo> getAllEnumListInfo() {
        List<EnumListInfo> list = new ArrayList<>();
        if (mappings.getMappings() != null) {
            list.addAll(mappings.getMappings().keySet().stream().map(key -> new EnumListInfo(key, "value-list")).toList());
        } else {
            log.warn("No Enum/ValueList configured");
        }
        final List<EnumListInfo> masterLists = Arrays.stream(MasterLists.values())
                .map(value -> new EnumListInfo(value.name(), value.getResource())).toList();
        list.addAll(masterLists);
        return list;
    }

    public record EnumListInfo(String value, String resourceName) {
    }
}
