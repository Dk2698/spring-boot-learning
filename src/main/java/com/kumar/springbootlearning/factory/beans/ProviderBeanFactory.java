package com.kumar.springbootlearning.factory.beans;

import com.kumar.springbootlearning.factory.exception.BadConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ProviderBeanFactory<T extends ProviderService> {
    private final Map<String, T> providers;

    public ProviderBeanFactory(ApplicationContext context) {
        final Map<String, T> beans = context.getBeansOfType(getProviderInterface());
        this.providers = new HashMap<>();
        beans.forEach((key, value) -> providers.put(value.getProviderName(), value));
    }

    public T getProvider(String providerName) {
        if (!providers.containsKey(providerName)) {
            log.error("Unable to find provider for {} with name {}", getProviderInterface(), providerName);
            throw new BadConfigurationException("Unable to find provider", getProviderInterface() + "#" + providerName);
        }
        return providers.get(providerName);
    }

    @SuppressWarnings("unchecked")
    public Class<T> getProviderInterface() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }
}