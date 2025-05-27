package com.kumar.springbootlearning.strategy;

import org.springframework.stereotype.Component;

@Component("defaultStrategy")
public class DefaultUrlStrategy implements ImageUrlModificationStrategy {

    @Override
    public String modifyUrl(String url) {
        return url;
    }

    @Override
    public void detectFileType(FileMeta fileMeta) {
    }
}