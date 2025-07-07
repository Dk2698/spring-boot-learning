package com.kumar.springbootlearning.pattern.strategy.service;

import com.kumar.springbootlearning.pattern.strategy.ImageUrlModificationStrategy;
import com.kumar.springbootlearning.pattern.strategy.UrlModificationStrategySelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UrlResolvedService {

    @Autowired
    private UrlModificationStrategySelector urlModificationStrategySelector;

    public void getUriResolved(String imageUri){
        ImageUrlModificationStrategy urlModificationStrategy = urlModificationStrategySelector.getStrategy(imageUri);
        String url=urlModificationStrategy.modifyUrl(imageUri);
        log.debug("public image uri: {}", url);
    }
}
