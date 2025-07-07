package com.kumar.springbootlearning.pattern.controller;

import com.kumar.springbootlearning.pattern.factory.ReportExporterFactory;
import com.kumar.springbootlearning.pattern.factory.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping("/formats")
    public List<String> getAvailableFormats() {
        return reportService.getAvailableFormats();
    }
}
