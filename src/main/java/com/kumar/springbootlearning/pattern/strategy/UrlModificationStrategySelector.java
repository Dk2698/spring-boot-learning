package com.kumar.springbootlearning.pattern.strategy;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UrlModificationStrategySelector {

    @Value("${url.pattern.google-drive}")
    private String googleDrivePatternString;

    @Value("${url.pattern.dropbox}")
    private String dropboxPatternString;

    private Pattern googleDrivePattern;

    private Pattern dropboxPattern;

    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        googleDrivePattern = Pattern.compile(googleDrivePatternString);
        dropboxPattern = Pattern.compile(dropboxPatternString);
    }

    public UrlModificationStrategySelector(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ImageUrlModificationStrategy getStrategy(String url) {
        if (googleDrivePattern.matcher(url).matches()) {
            return (ImageUrlModificationStrategy) applicationContext.getBean("googleDriveStrategy");
        } else if (dropboxPattern.matcher(url).matches()) {
            return (ImageUrlModificationStrategy) applicationContext.getBean("dropboxStrategy");
        } else {
            return (ImageUrlModificationStrategy) applicationContext.getBean("defaultStrategy");
        }
    }
}
