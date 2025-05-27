package com.kumar.springbootlearning.async;

import com.kumar.springbootlearning.async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
@Slf4j
public class AsyncController {

    private  final AsyncService asyncService;

    public AsyncController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping("/async")
    public String triggerAsyncFuture1(){
        log.debug("Rest controller");
        asyncService.asyncMethod();
        return "Async method triggered!";
    }

    @GetMapping("/trigger-async")
    public String triggerAsync() {
        asyncService.performAsyncTask();
        return "Async task triggered";
    }

    @GetMapping("/trigger-async-future")
    public CompletableFuture<String> triggerAsyncFuture() {
        return asyncService.asyncMethod();
    }
}
