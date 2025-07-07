package com.kumar.springbootlearning.pattern.template;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileUploadHandlerFactory {

    private final Map<String, FileUploadTemplate> handlers;

    public FileUploadHandlerFactory(List<FileUploadTemplate> handlerList) {
        this.handlers = new HashMap<>();
        handlers.put("csv", getHandler(handlerList, CSVUploadHandler.class));
        handlers.put("xlsx", getHandler(handlerList, ExcelUploadHandler.class));
    }

    private FileUploadTemplate getHandler(List<FileUploadTemplate> list, Class<?> type) {
        return list.stream()
                .filter(type::isInstance)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No handler for type: " + type));
    }

    public FileUploadTemplate getHandlerForExtension(String ext) {
        return handlers.get(ext.toLowerCase());
    }
}
