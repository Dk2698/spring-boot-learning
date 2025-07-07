package com.kumar.springbootlearning.pattern.factory;

import com.kumar.springbootlearning.factory.beans.ProviderService;

public interface ReportExporter extends ProviderExportType {
    void export(String reportData);
}
