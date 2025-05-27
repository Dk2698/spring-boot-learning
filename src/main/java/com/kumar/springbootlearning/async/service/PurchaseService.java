package com.kumar.springbootlearning.async.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    @Autowired
    private EmailService emailService;

    public void purchase(){
        try{
            emailService.sendEmail();
        }catch (Exception e){
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
}
