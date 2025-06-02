package com.kumar.springbootlearning.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {

    // Handler method to handle

    @GetMapping("/hello-world")
    public String helloWorld(Model model){
        model.addAttribute("message", "Hello World!");
        return "hello-world";
    }
}
