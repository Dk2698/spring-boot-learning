package com.kumar.springbootlearning.pattern.strategy;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileMeta implements Serializable {
    private String fileName;
    private Long fileSize;
    private String fileType;
    /**
     * Base64 encoded file content
     */
    private String fileContent;
}