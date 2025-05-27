package com.kumar.springbootlearning.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

    //Asynchronous programming enables a method to run in the background without blocking the main thread.
    // This approach is particularly useful in scenarios where a task might take a long time to complete, such as:
    //Making external API calls
    //Performing I/O operations (e.g., reading/writing files)
    //Running complex calculations
    //With asynchronous calls, the application can continue executing other tasks while the long-running process completes,
    // thus improving the overall efficiency and responsiveness.
    @Async
    public CompletableFuture<String> asyncMethod() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture("Hello from async!");
    }

    @Async
    public void performAsyncTask() {
        System.out.println("Start async task: " + Thread.currentThread().getName());
        try {
            Thread.sleep(5000); // Simulate a long-running task
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Completed async task: " + Thread.currentThread().getName());
    }
}
