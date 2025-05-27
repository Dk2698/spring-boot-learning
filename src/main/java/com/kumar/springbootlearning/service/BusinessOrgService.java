package com.kumar.springbootlearning.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumar.springbootlearning.entity.BusinessOrg;
import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import com.kumar.springbootlearning.mvc.remote.BaseRemoteService;
import com.kumar.springbootlearning.service.dto.BusinessOrgDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BusinessOrgService extends BaseRemoteService<String, BusinessOrgDTO, BusinessOrg> {
    protected BusinessOrgService(WebClient webClient, ComDelegateMapping mappings, ObjectMapper objectMapper) {
        super(webClient, mappings, objectMapper);
    }

    @Override
    protected String getEntityResourceName() {
        return "business-orgs";
    }

    @Override
    public String getTargetService() {
        return "abc-core";
    }
}
