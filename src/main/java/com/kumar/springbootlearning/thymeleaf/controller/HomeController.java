package com.kumar.springbootlearning.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "Spring Boot + Thymeleaf");
        return "hello";  // Refers to hello.html in templates folder
    }
}