package com.kumar.springbootlearning.entity;

import com.kumar.springbootlearning.data.model.AuditableBaseEntity;
import com.kumar.springbootlearning.valuelist.enums.OrgType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BusinessOrg extends AuditableBaseEntity<String> {

//    @NotBlank
//    @Size(max = 20)
//    @BusinessKey
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

    private String businessEntityId;

    private String taxIdentifier;

//    private UspPreferences preferences;
}
