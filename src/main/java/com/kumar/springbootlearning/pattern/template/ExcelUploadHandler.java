package com.kumar.springbootlearning.pattern.template;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ExcelUploadHandler extends FileUploadTemplate {

    @Override
    protected Object parse(MultipartFile file) {
        System.out.println("Parsing Excel file: " + file.getOriginalFilename());
        // parse Excel logic
        return List.of("sheet1", "sheet2");
    }
}
