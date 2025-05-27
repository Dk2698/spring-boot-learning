package com.kumar.springbootlearning.data.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity<ID> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private ID id;

    @Version
    private int version;

    @Builder.Default
    private boolean deleted = false;

}