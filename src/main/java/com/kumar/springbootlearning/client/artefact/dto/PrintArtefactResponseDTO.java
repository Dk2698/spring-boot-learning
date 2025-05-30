package com.kumar.springbootlearning.client.artefact.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PrintArtefactResponseDTO implements Serializable {

    private String workUnitId;
    private String id;
    private String signedUrl;
    private Boolean aggregate;

}
