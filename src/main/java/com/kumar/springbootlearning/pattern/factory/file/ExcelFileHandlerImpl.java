package com.kumar.springbootlearning.pattern.factory.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Slf4j
public class ExcelFileHandlerImpl implements FileHandler {
//    private File file;
//    private AbstractUploadRequest request;
//    private LoaderConfig config;
//    private DefaultConfigs defaultConfigs;

//    @Override
//    public void init(File file, AbstractUploadRequest request, LoaderConfig config, DefaultConfigs defaultConfigs) {
//        this.file = file;
//        this.request = request;
//        this.config = config;
//        this.defaultConfigs = defaultConfigs;
//    }

    @Override
    public void handle() {
//        log.info("Handling Excel file: {}", file.getName());
    }

    @Override
    public String getFileExtension() {
        return "xlsx";
    }
}


