package com.kumar.springbootlearning.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kumar.springbootlearning.data.model.EntityDTO;
import com.kumar.springbootlearning.valuelist.enums.OrgType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BusinessOrgDTO implements EntityDTO<String> {
    private String id;
    private Integer version;

    //    @NotBlank
//    @Size(max = 20)
//    @UniqueConstraint(domainClass = BusinessOrg.class, attribute = "orgCode")
    private String orgCode;

//    @NotBlank
    private String legalName;
    /**
     * refer
     */
//    @NotNull
    private OrgType orgType;

//    @NotNull
//    private Address contactAddress;
//
//    @NotNull
//    private ContactDetails contactDetails;
    /**
     * businessEntityId refer to BusinessEntity in uspr
     */
    private String businessEntityId;

    private String taxIdentifier;

//    private UspPreferences preferences;
//
//    private FileMeta placeholderImage1File;
//
//    private FileMeta placeholderImage2File;
//
//    private FileMeta placeholderImage3File;
//
//    private FileMeta signatureImageFile;

    /**
     * isJanusCall true means the creation request is coming from JANUS
     */
    private Boolean isJanusCall;
}
