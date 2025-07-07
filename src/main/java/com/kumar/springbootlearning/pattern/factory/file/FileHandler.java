package com.kumar.springbootlearning.pattern.factory.file;

public interface FileHandler {
//    void init(File file, AbstractUploadRequest request, LoaderConfig config, DefaultConfigs defaultConfigs);
    void handle(); // your custom method
    String getFileExtension(); // like "csv", "xlsx"
}

