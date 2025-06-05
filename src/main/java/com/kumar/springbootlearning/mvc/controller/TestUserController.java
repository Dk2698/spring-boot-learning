package com.kumar.springbootlearning.mvc.controller;

import com.kumar.springbootlearning.mvc.exception.InvalidRequestException;
import com.kumar.springbootlearning.mvc.exception.ResourceNotFoundException;
import com.kumar.springbootlearning.thymeleaf.model.User;
import com.kumar.springbootlearning.thymeleaf.model.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class TestUserController {

    @GetMapping("/{id}/test")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        User user = userService.findById(id);
        User user = null;
        if (user == null) {
            throw new ResourceNotFoundException("User not found with ID: " + id, "error.code", "error.details");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id) {
        if (id < 1) {
            throw new InvalidRequestException("User ID must be greater than zero.");
        }
        if (id != 100) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.", "error.code", "error.details");
        }
        return "User with ID: " + id;
    }

    /**
     * {
     *   "type": "https://example.com/errors/not-found",
     *   "title": "Resource Not Found",
     *   "status": 404,
     *   "detail": "User with ID 5 not found.",
     *   "instance": "/api/users/5",
     *   "errorCode": "RESOURCE_NOT_FOUND",
     *   "timestamp": "2025-06-05T10:30:00.123456"
     * }
     */

    @PostMapping
    public String createUser(@RequestBody @Valid UserRequest userRequest) {
        return "User created: " + userRequest.getName();
    }
}
