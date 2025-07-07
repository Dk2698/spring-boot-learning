package com.kumar.springbootlearning.pattern.factory.file;

import org.springframework.stereotype.Service;

@Service
public class FileUploadService {

    private final FileHandlerFactory handlerFactory;
//    private final DefaultConfigs defaultConfigs;

//    public FileUploadService(FileHandlerFactory handlerFactory, DefaultConfigs defaultConfigs) {
//        this.handlerFactory = handlerFactory;
//        this.defaultConfigs = defaultConfigs;
//    }

    public FileUploadService(FileHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
//        this.defaultConfigs = defaultConfigs;
    }

//    public void handleUpload(File file, AbstractUploadRequest request, LoaderConfig config) {
//        String extension = getFileExtension(file.getName());
//
//        FileHandler handler = handlerFactory.getHandlerByExtension(extension);
//        handler.init(file, request, config, defaultConfigs);
//        handler.handle();
//    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }
}
