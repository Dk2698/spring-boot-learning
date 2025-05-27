package com.kumar.springbootlearning.async.config;

import com.kumar.springbootlearning.singleton.context.TenantContext;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class ContextPropagationDecorator implements TaskDecorator {
    private final TenantContext tenantContext;

    public ContextPropagationDecorator(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    @Override
    public Runnable decorate(Runnable runnable) {
//        SecurityContext context = SecurityContextHolder.getContext();
        Map<String, String> mdcContext = MDC.getCopyOfContextMap();
        Map<String, String> tenantContextMap = tenantContext.getCopyOfContextMap();

        return () -> {
            try {
//                SecurityContextHolder.setContext(context);
                if (mdcContext != null) {
                    MDC.setContextMap(mdcContext);
                }
                if (tenantContextMap != null) {
                    tenantContext.setContextMap(tenantContextMap);
                }
                runnable.run();
            } finally {
//                SecurityContextHolder.clearContext();
                MDC.clear();
                tenantContext.clear();
            }
        };
    }

    private class SecurityContextHolder {
    }
}