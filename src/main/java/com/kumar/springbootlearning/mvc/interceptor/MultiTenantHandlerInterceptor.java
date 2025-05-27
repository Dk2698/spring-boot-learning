package com.kumar.springbootlearning.mvc.interceptor;

import com.kumar.springbootlearning.singleton.context.TenantContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.TreeMap;

@Component
@Slf4j
public class MultiTenantHandlerInterceptor implements HandlerInterceptor {
    public static final String X_TENANT_ID = "X-Tenant-Id";
    public static final String X_POD_ID = "X-Pod-Id";
    public static final String X_APP_ID = "X-App-Id";
    public static final String POD_ID = "pod_id";
    public static final String TENANT_ID = "tenant_id";
    public static final String APP_ID = "app_id";

    @Autowired
    private TenantContext tenantContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        setTenantId(request);
        setDeploymentPodId(request);
        setApplicationId(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        tenantContext.clear();
    }

    private void setTenantId(HttpServletRequest request) {
        // Header that would be attached to every request made to identify tenant DB.
        String tenantId = request.getHeader(X_TENANT_ID);
        //OR can look into request parameter that can help identify tenant DB
        if (tenantId == null && request.getParameterMap() != null) {
            String[] clientCodes = request.getParameterMap().get(TENANT_ID);
            if (clientCodes != null && clientCodes.length > 0) {
                tenantId = clientCodes[0];
            }
        }
        // or can look into PATH Variables/attribute.
        if (tenantId == null) {
            Object o = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (o instanceof Map) {
                var map = new TreeMap<>((Map<String, String>) o);
                tenantId = map.get(TENANT_ID);
            }
        }
        log.info("Tenant Id : {}", tenantId);
        tenantContext.setTenantId(tenantId);
    }

    private void setDeploymentPodId(HttpServletRequest request) {
        // Header that would be attached to every request made to identify tenant DB.
        String podId = request.getHeader(X_POD_ID);
        //OR can look into request parameter that can help identify tenant DB
        if (podId == null && request.getParameterMap() != null) {
            String[] clientCodes = request.getParameterMap().get(POD_ID);
            if (clientCodes != null && clientCodes.length > 0) {
                podId = clientCodes[0];
            }
        }
        if (podId == null) {
            // or can look into PATH Variables/attribute.
            Object o = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (o instanceof Map) {
                var map = new TreeMap<>((Map<String, String>) o);
                podId = map.get(POD_ID);
            }
        }
        log.info("Deployment Pod : {}", podId);
        tenantContext.setDeploymentPodId(podId);
    }

    private void setApplicationId(HttpServletRequest request) {
        // Header that would be attached to every request made to identify tenant DB.
        String appId = request.getHeader(X_APP_ID);
        //OR can look into request parameter that can help identify tenant DB
        if (appId == null && request.getParameterMap() != null) {
            String[] clientCodes = request.getParameterMap().get(POD_ID);
            if (clientCodes != null && clientCodes.length > 0) {
                appId = clientCodes[0];
            }
        }
        if (appId == null) {
            // or can look into PATH Variables/attribute.
            Object o = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (o instanceof Map) {
                var map = new TreeMap<>((Map<String, String>) o);
                appId = map.get(APP_ID);
            }
        }
        log.info("Application Id : {}", appId);
        tenantContext.setApplicationId(appId);
    }
}