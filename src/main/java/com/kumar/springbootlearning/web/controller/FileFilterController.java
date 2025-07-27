package com.kumar.springbootlearning.web.controller;

import com.kumar.springbootlearning.entity.FileInfo;
import com.kumar.springbootlearning.service.FileFilterService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileFilterController {

    private final FileFilterService fileFilterService;

    public FileFilterController(FileFilterService fileFilterService) {
        this.fileFilterService = fileFilterService;
    }

    @GetMapping
    public ResponseEntity<?> listFiles(
            @RequestParam String path,
            @RequestParam(required = false) List<String> ext,
            @RequestParam(required = false) String nameContains,
            @RequestParam(required = false) String nameStarts,
            @RequestParam(required = false) Long minSize,
            @RequestParam(required = false) Long maxSize,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime modifiedAfter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime modifiedBefore
    ) {
        try {
            List<FileInfo> filteredFiles = fileFilterService.filterFiles(
                    path,
                    ext,
                    nameContains,
                    nameStarts,
                    minSize,
                    maxSize,
                    modifiedAfter,
                    modifiedBefore
            );

            Map<String, Object> response = new HashMap<>();
            response.put("path", path);
            response.put("files", filteredFiles);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
