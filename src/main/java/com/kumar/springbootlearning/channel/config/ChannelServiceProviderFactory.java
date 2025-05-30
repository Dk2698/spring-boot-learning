package com.kumar.springbootlearning.channel.config;

import com.kumar.springbootlearning.channel.service.ChannelIntegrationService;
import com.kumar.springbootlearning.factory.beans.ProviderBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ChannelServiceProviderFactory extends ProviderBeanFactory<ChannelIntegrationService> {
    public ChannelServiceProviderFactory(ApplicationContext context) {
        super(context);
    }
}