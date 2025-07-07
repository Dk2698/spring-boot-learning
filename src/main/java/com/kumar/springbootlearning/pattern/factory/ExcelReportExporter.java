package com.kumar.springbootlearning.pattern.factory;

import org.springframework.stereotype.Service;

@Service
public class ExcelReportExporter implements ReportExporter {
    public void export(String reportData) {
        System.out.println("Exporting report as Excel: " + reportData);
    }

    @Override
    public String getExportType() {
        return ExportType.EXCEL.name();
    }
}