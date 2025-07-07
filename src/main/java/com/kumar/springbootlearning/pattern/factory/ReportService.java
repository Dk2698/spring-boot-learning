package com.kumar.springbootlearning.pattern.factory;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class ReportService {

    private final ReportExporterFactory exporterFactory;

    public ReportService(ReportExporterFactory exporterFactory) {
        this.exporterFactory = exporterFactory;
    }

    public void exportReport(String data, ExportType type) {
        ReportExporter exporter = exporterFactory.getProvider(type.name());
        exporter.export(data);
    }

    public List<String> getAvailableFormats() {
        return exporterFactory.getAvailableProviders();
    }
}

