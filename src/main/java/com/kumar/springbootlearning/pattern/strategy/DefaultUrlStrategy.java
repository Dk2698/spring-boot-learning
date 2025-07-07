package com.kumar.springbootlearning.pattern.strategy;

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