package com.kumar.springbootlearning.web.controller;

import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import com.kumar.springbootlearning.mvc.controller.BaseProxyController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/mool")
@Slf4j
public class MoolController  extends BaseProxyController<String> {

    public MoolController(WebClient webClient, ComDelegateMapping mappings) {
        super(webClient,mappings);
    }

    @Override
    public String getTargetService() {
        return "mool";
    }
}

