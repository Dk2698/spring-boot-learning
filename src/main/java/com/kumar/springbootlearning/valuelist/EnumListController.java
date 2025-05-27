package com.kumar.springbootlearning.valuelist;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enum-lists")
@Slf4j
public class EnumListController extends BaseController {

    private final EnumValuesService enumValuesService;

    public EnumListController(EnumValuesService enumValuesService) {

        this.enumValuesService = enumValuesService;
    }

    @GetMapping()
    public ResponseEntity<List<EnumValuesService.EnumListInfo>> getAllEnumKeys() {
        log.debug("REST request to get Enum Keys ");
        final List<EnumValuesService.EnumListInfo> list = enumValuesService.getAllEnumListInfo();
        return ResponseEntity.ok().body(list);
    }
}