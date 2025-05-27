package com.kumar.springbootlearning.strategy;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("googleDriveStrategy")
public class GoogleUrlModificationStrategy implements ImageUrlModificationStrategy {

    @Value("${google.drive.download.url}")
    private String driveDownloadUrlTemplate;

    @Value("${url.pattern.google-drive-file-id}")
    private String googleDriveFileIdPatternString;

    private Pattern googleDriveFileIdPattern;

    @PostConstruct
    public void init() {
        googleDriveFileIdPattern = Pattern.compile(googleDriveFileIdPatternString);
    }

    @Override
    public String modifyUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        String fileId = extractFileId(url);
        if (fileId == null) {
            throw new IllegalArgumentException("Invalid Google Drive URL: " + url);
        }
        return driveDownloadUrlTemplate.replace("{fileId}", fileId);
    }

    @Override
    public void detectFileType(FileMeta fileMeta) {
    }

    private String extractFileId(String url) {
        Matcher matcher = googleDriveFileIdPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1); // The first capturing group contains the file ID
        }
        return null;
    }
}