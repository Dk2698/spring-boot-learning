package com.kumar.springbootlearning.pattern.template;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class CSVUploadHandler extends FileUploadTemplate {

    @Override
    protected Object parse(MultipartFile file) {
        System.out.println("Parsing CSV file: " + file.getOriginalFilename());
        // parse CSV logic
        return List.of("row1", "row2");
    }
}

