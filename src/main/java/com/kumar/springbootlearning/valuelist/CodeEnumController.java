package com.kumar.springbootlearning.valuelist;

import com.kumar.springbootlearning.valuelist.config.BaseEnum;
import com.kumar.springbootlearning.valuelist.config.CodeEnum;
import com.kumar.springbootlearning.valuelist.config.ComValueListMapping;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/value-lists")
@Slf4j
public class CodeEnumController extends BaseController {

    private final ComValueListMapping mappings;

    public CodeEnumController(ComValueListMapping mappings) {
        this.mappings = mappings;
    }

    @GetMapping("/{enumKey}")
    public ResponseEntity<List<EnumValue>> getAllValues(@PathVariable(value = "enumKey") String enumKey, HttpServletRequest request) {
        log.debug("REST request to get values of {}", enumKey);
        List<EnumValue> list = new ArrayList<>();
        if (mappings.getMappings() != null) {
            final String enumClassName = mappings.getMappings().get(enumKey);
            try {
                final Class<?> enumClass = Class.forName(enumClassName);
                if (enumClass.isEnum()) {
                    final Enum<?>[] enumConstants = (Enum<?>[]) enumClass.getEnumConstants();
                    list = Arrays.stream(enumConstants).map(x -> {
                        if (x instanceof BaseEnum baseEnum) {
                            return new EnumValue(x.name(), baseEnum.getDescription(), baseEnum.getDescription());
                        } else if (x instanceof CodeEnum codeEnum) {
                            return new EnumValue(x.name(), codeEnum.getCode(), codeEnum.getDescription());
                        } else {
                            return new EnumValue(x.name(), x.name(), x.name());
                        }
                    }).toList();
                } else {
                    log.error("Invalid configuration for EnumList {}. It must be valid Enum. ", enumKey);
                }
            } catch (ClassNotFoundException e) {
                log.error("Invalid configuration for EnumList {}. It must be valid Enum. Exception -  ", enumKey, e.getException());
            }
        } else {
            log.error("Invalid configuration for EnumList {}. No entry found in configuration for logistiex.value-lists.mappings.{} ", enumKey, enumKey);
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping()
    public ResponseEntity<List<EnumValue>> getAllEnumKeys(HttpServletRequest request) {
        log.debug("REST request to get Enum Keys ");

        List<EnumValue> list = new ArrayList<>();
        if (mappings.getMappings() != null) {
            list = mappings.getMappings().keySet().stream().map(key -> new EnumValue(key, key, key)).toList();
        } else {
            log.warn("No Enum/ValueList configured");
        }
        return ResponseEntity.ok().body(list);
    }

    record EnumValue(String value, String code, String description) {
    }
}
