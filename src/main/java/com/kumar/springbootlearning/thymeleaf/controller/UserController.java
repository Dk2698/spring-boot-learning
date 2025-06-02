package com.kumar.springbootlearning.thymeleaf.controller;

import com.kumar.springbootlearning.thymeleaf.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @GetMapping("/variable-expression")
    public String variableExpression(Model model){
        User user = new User("Kumar", "kumar@gmail.com","ADMIN","MALE");
        model.addAttribute("user", user);
        return "variable-expression";
    }

    @GetMapping("/selection-expression")
    public String selectionExpression(Model model){
        User user = new User("Kumar", "kumar@gmail.com","ADMIN","MALE");
        model.addAttribute("user", user);
        return "selection-expression";
    }

    @GetMapping("/message-expression")
    public String messageExpression(){
        return "message-expression";
    }

    @GetMapping("/link-expression")
    public String linkExpression(Model model){
        model.addAttribute("id", 1);
        return "link-expression";
    }

    @GetMapping("/fragment-expression")
    public String fragmentExpression(Model model){
        return "fragment-expression";
    }

    @GetMapping("/users")
    public String users(Model model){
        User admin = new User("Kumar", "kumar@gmail.com","ADMIN","MALE");
        User john = new User("John", "john@gmail.com","ADMIN","MALE");
        User vinay = new User("Vinay", "Vinay@gmail.com","USER","MALE");
        User anna = new User("Anna", "anna@gmail.com","USER","FEMALE");

        List<User> users = new ArrayList<>();
        users.add(admin);
        users.add(john);
        users.add(anna);
        users.add(vinay);

        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/if-unless")
    public String ifUnless(Model model){
        User admin = new User("Kumar", "kumar@gmail.com","ADMIN","MALE");
        User john = new User("John", "john@gmail.com","ADMIN","MALE");
        User vinay = new User("Vinay", "Vinay@gmail.com","USER","MALE");
        User anna = new User("Anna", "anna@gmail.com","USER","FEMALE");

        List<User> users = new ArrayList<>();
        users.add(admin);
        users.add(john);
        users.add(anna);
        users.add(vinay);

        model.addAttribute("users", users);
        return "if-unless";
    }

    @GetMapping("/switch-case")
    public String switchCase(Model model){
        User user = new User("Kumar", "kumar@gmail.com","ADMIN","MALE");

        model.addAttribute("user", user);
        return "switch-case";
    }
}
