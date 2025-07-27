package com.kumar.springbootlearning.service;

import com.kumar.springbootlearning.entity.FileInfo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FileFilterService {

    public List<FileInfo> filterFiles(
            String path,
            List<String> extensions,
            String nameContains,
            String nameStarts,
            Long minSize,
            Long maxSize,
            LocalDateTime modifiedAfter,
            LocalDateTime modifiedBefore
    ) {
        File directory = new File(path);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory path");
        }

        File[] files = directory.listFiles(File::isFile);
        if (files == null) return Collections.emptyList();

        return Arrays.stream(files)
                .filter(file -> {
                    if (extensions != null && !extensions.isEmpty()) {
                        String ext = getExtension(file.getName());
                        if (!extensions.contains(ext)) return false;
                    }

                    if (nameContains != null && !file.getName().contains(nameContains)) return false;
                    if (nameStarts != null && !file.getName().startsWith(nameStarts)) return false;
                    if (minSize != null && file.length() < minSize) return false;
                    if (maxSize != null && file.length() > maxSize) return false;

                    long lastModified = file.lastModified();
                    if (modifiedAfter != null && lastModified < toMillis(modifiedAfter)) return false;
                    if (modifiedBefore != null && lastModified > toMillis(modifiedBefore)) return false;

                    return true;
                })
                .map(file -> new FileInfo(
                        file.getName(),
                        file.length(),
                        Instant.ofEpochMilli(file.lastModified()).toString(),
                        getExtension(file.getName())
                ))
                .collect(Collectors.toList());
    }

    private String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex != -1 && dotIndex < fileName.length() - 1) ?
                fileName.substring(dotIndex + 1).toLowerCase() : "";
    }

    private long toMillis(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}