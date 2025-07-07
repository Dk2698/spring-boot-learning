package com.kumar.springbootlearning.pattern.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ReportExporterFactory extends ProviderBeanFactory<ReportExporter> {
    public ReportExporterFactory(ApplicationContext context) {
        super(context);
    }
}

