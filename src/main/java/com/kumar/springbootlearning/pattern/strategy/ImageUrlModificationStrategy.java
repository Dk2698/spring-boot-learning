package com.kumar.springbootlearning.pattern.strategy;

public interface ImageUrlModificationStrategy {
    String modifyUrl(String url);
    void detectFileType(FileMeta fileMeta);
}