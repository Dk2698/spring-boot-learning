package com.kumar.springbootlearning.pattern.template;

import org.springframework.web.multipart.MultipartFile;

public abstract class FileUploadTemplate {

    // Template method
    public final void processFile(MultipartFile file) {
        validate(file);
        Object parsedData = parse(file); // subclass implements this
        save(parsedData);
    }

    protected void validate(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        System.out.println("Generic validation passed for " + file.getOriginalFilename());
    }

    protected abstract Object parse(MultipartFile file); // Subclass provides

    protected void save(Object data) {
        System.out.println("Saving parsed data: " + data);
    }
}
