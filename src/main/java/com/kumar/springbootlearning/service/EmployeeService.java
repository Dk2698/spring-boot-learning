package com.kumar.springbootlearning.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeService {

    public String fetchEmployee() {
        return "fetch employee";
    }
}
