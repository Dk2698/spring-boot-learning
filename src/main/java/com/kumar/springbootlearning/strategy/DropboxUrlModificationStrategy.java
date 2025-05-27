package com.kumar.springbootlearning.strategy;


import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component("dropboxStrategy")
public class DropboxUrlModificationStrategy implements ImageUrlModificationStrategy {

    @Override
    public String modifyUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        if (url.contains("dl=")) {
            return url.replace("dl=0", "dl=1");
        }
        return url;
    }

    @Override
    public void detectFileType(FileMeta fileMeta) {
        try {
            byte[] fileBytes = Base64.getDecoder().decode(fileMeta.getFileContent());
            Tika tika = new Tika();
            fileMeta.setFileName(tika.detect(fileBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
