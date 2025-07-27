package com.kumar.springbootlearning.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileInfo {
    private String name;
    private long size;
    private String modified;
    private String extension;

    public FileInfo(String name, long size, String modified, String extension) {
        this.name = name;
        this.size = size;
        this.modified = modified;
        this.extension = extension;
    }
}