package com.kumar.springbootlearning.pattern.factory;

import org.springframework.stereotype.Service;

@Service
public class CsvReportExporter implements ReportExporter {
    public void export(String reportData) {
        System.out.println("Exporting report as CSV: " + reportData);
    }

    @Override
    public String getExportType() {
        return ExportType.CSV.name();
    }
}