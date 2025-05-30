package com.kumar.springbootlearning.client.artefact.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kumar.springbootlearning.client.artefact.vo.Shipment;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PrintArtefactRequestDTO implements Serializable {

    private String orgCode;

    private String templateGroup;

    private List<Shipment> data;

    private String contextIdAttribute;

    private String subContextId;

    private String type;

    private String useCase;

    private String fileName;

    private Boolean aggregate;
}
