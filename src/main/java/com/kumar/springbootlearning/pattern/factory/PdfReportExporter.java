package com.kumar.springbootlearning.pattern.factory;

import org.springframework.stereotype.Service;

@Service
public class PdfReportExporter implements ReportExporter {
    public void export(String reportData) {
        System.out.println("Exporting report as PDF: " + reportData);
    }

    @Override
    public String getExportType() {
        return ExportType.PDF.name();
    }
}