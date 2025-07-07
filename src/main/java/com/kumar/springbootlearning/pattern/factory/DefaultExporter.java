package com.kumar.springbootlearning.pattern.factory;

import org.springframework.stereotype.Service;

@Service
public class DefaultExporter implements ReportExporter {
    @Override public void export(String data) {
        System.out.println("Exporting as plain text fallback: " + data);
    }

    @Override public String getExportType() {
        return "default";
    }
}
