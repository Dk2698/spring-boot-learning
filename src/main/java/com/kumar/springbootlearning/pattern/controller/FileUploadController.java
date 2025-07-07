package com.kumar.springbootlearning.pattern.controller;

import com.kumar.springbootlearning.pattern.template.FileUploadHandlerFactory;
import com.kumar.springbootlearning.pattern.template.FileUploadTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private final FileUploadHandlerFactory handlerFactory;

    public FileUploadController(FileUploadHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    @PostMapping
    public String upload(@RequestParam MultipartFile file) {
        String ext = getExtension(file.getOriginalFilename());
        FileUploadTemplate handler = handlerFactory.getHandlerForExtension(ext);
        handler.processFile(file);
        return "Uploaded " + file.getOriginalFilename();
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}
