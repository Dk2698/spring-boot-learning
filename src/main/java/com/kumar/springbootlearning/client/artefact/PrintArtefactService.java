package com.kumar.springbootlearning.client.artefact;

import com.kumar.springbootlearning.client.artefact.dto.PrintArtefactRequestDTO;
import com.kumar.springbootlearning.client.artefact.dto.PrintArtefactResponseDTO;
import com.kumar.springbootlearning.factory.exception.BadRequestException;
import com.kumar.springbootlearning.mvc.config.ComDelegateMapping;
import com.kumar.springbootlearning.mvc.exception.BadConfigurationException;
import com.kumar.springbootlearning.mvc.exception.RemoteInvocationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PrintArtefactService {

    private final WebClient webClient;

    private final ComDelegateMapping mappings;


    public PrintArtefactService(@Qualifier("webClient") WebClient webClient, ComDelegateMapping mappings) {
        this.webClient = webClient;
        this.mappings = mappings;
    }

    public String getTargetUri() {
        final Map<String, String> map = mappings.getUri();
        if (map.containsKey("template")) {
            return map.get("template");
        } else {
            throw new BadConfigurationException("error.missing.delegate", "Unable to find delegate", "com.delegate.service.uri.template");
        }
    }

    public List<PrintArtefactResponseDTO> printArtefact(PrintArtefactRequestDTO dto) {
        final String uri = getTargetUri() + "/v2/artefact/generate-link" ;
        Mono<List<PrintArtefactResponseDTO>> retrievedResource = webClient.post()
                .uri(uri)
                .bodyValue(dto)
                .retrieve()
                .bodyToFlux(PrintArtefactResponseDTO.class)
                .collectList();

        log.info(retrievedResource.toString());
        log.info(uri);

        try {
            final List<PrintArtefactResponseDTO> clientResponse = retrievedResource.block();
            if (clientResponse != null) {
                log.info( clientResponse.toString());
                return clientResponse;
            } else {
                log.error("Invalid input for Print Artefact Service.");
                throw new BadRequestException("Invalid input ", "Not Found response.", "invalid_input");
            }
        } catch (Exception ex) {
            log.error("Error while calling Print Artefact Service.", ex);
            throw new RemoteInvocationException("Error while calling Print Artefact Service.", ex.getMessage(), ex);
        }
    }

}
