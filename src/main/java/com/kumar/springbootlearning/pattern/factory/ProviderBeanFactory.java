package com.kumar.springbootlearning.pattern.factory;

import com.kumar.springbootlearning.factory.exception.BadConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public abstract class ProviderBeanFactory<T extends ProviderExportType> {

    private final Map<String, T> providers;
    private final T defaultProvider; // Optional fallback

    public ProviderBeanFactory(ApplicationContext context) {
        final Map<String, T> beans = context.getBeansOfType(getProviderInterface());
        this.providers = new HashMap<>();

        AtomicReference<T> tempDefault = new AtomicReference<>();

        beans.forEach((beanName, beanInstance) -> {
            String key = normalize(beanInstance.getExportType());
            providers.put(key, beanInstance);

            // Optional: auto-assign default if one has exportType "default"
            if ("default".equalsIgnoreCase(beanInstance.getExportType())) {
                tempDefault.set(beanInstance);
            }
        });

        this.defaultProvider = tempDefault.get();
    }

    /**
     * Get provider by name. Case-insensitive. Returns fallback if available.
     */
    public T getProvider(String providerName) {
        String key = normalize(providerName);

        if (!providers.containsKey(key)) {
            if (defaultProvider != null) {
                log.warn("Provider '{}' not found. Falling back to default: {}", providerName, defaultProvider.getClass().getSimpleName());
                return defaultProvider;
            }
            log.error("Unable to find provider '{}' for {}", providerName, getProviderInterface());
            throw new BadConfigurationException("No provider found", getProviderInterface() + "#" + providerName);
        }

        return providers.get(key);
    }

    /**
     * Get list of all available provider keys.
     */
    public List<String> getAvailableProviders() {
        return new ArrayList<>(providers.keySet());
    }

    /**
     * Normalize keys for consistent case-insensitive lookup.
     */
    private String normalize(String key) {
        return key == null ? "" : key.trim().toLowerCase();
    }

    /**
     * Determine the interface this factory is managing.
     */
    @SuppressWarnings("unchecked")
    public Class<T> getProviderInterface() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }
}
