package com.kumar.springbootlearning.singleton.context;

import org.springframework.stereotype.Component;

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
}