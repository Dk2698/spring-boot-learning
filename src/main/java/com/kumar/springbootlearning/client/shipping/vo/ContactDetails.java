package com.kumar.springbootlearning.client.shipping.vo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "encrypted", defaultImpl = ContactDetails.class )
//@JsonSubTypes({@JsonSubTypes.Type(value = EncryptedContactDetails.class, name = "true")})
public class ContactDetails implements Serializable {

    private String primaryPhone;
    private String alternatePhone;
//    @Email
    private String email;
    private String whatsappContact;
    private Map<String, String> additionalContacts = new HashMap<>();

}