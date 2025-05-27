package com.kumar.springbootlearning.singleton.context;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class TenantContext {
    private final ThreadLocal<String> tenantId = new ThreadLocal<>();

    private final ThreadLocal<String> deploymentPodId = new ThreadLocal<>();
    private final ThreadLocal<String> applicationId = new ThreadLocal<>();

    public String getDeploymentPodId() {
        return this.deploymentPodId.get();
    }

    public void setDeploymentPodId(final String deploymentPodId) {
        this.deploymentPodId.set(deploymentPodId);
    }

    public String getTenantId() {
        return tenantId.get();
    }

    public void setTenantId(final String tenantId) {
        this.tenantId.set(tenantId);
    }

    public String getApplicationId() {
        return applicationId.get();
    }

    public void setApplicationId(String applicationId) {
        this.applicationId.set(applicationId);
    }


    public void clear() {
        tenantId.remove();
        deploymentPodId.remove();
        applicationId.remove();
    }

    public Map<String, String> getCopyOfContextMap() {
        Map<String, String> map = new HashMap<>();

        if (tenantId.get() != null) {
            map.put("tenantId", tenantId.get());
        }
        if (deploymentPodId.get() != null) {
            map.put("deploymentPodId", deploymentPodId.get());
        }
        if (applicationId.get() != null) {
            map.put("applicationId", applicationId.get());
        }

        return Collections.unmodifiableMap(map);
    }

    public void setContextMap(Map<String, String> contextMap) {
        this.tenantId.set(contextMap.get("tenantId"));
        this.deploymentPodId.set(contextMap.get("deploymentPodId"));
        this.applicationId.set(contextMap.get("applicationId"));
    }
}