package com.kumar.springbootlearning.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

//    @Transactional
    public void transactionalMethod() {
        //database operation 1
        asyncMethod();
        //database operation 2
    }

    @Async
    public void asyncMethod() {
        //database operation 3
    }

    @Async("taskExecutorForHeavyTasks")
    public void sendEmailHeavy() {
        //method implementation
    }

    @Async
    public void sendEmail() throws Exception{
        throw new Exception("Oops, cannot send email!");
    }
}