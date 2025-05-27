package com.kumar.springbootlearning.strategy;

public interface ImageUrlModificationStrategy {
    String modifyUrl(String url);
    void detectFileType(FileMeta fileMeta);
}