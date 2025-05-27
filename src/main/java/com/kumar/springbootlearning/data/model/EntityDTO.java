package com.kumar.springbootlearning.data.model;

import java.io.Serializable;

/**
 * Placeholder interface for All EntityDTO. Required for Generic CrudService
 *
 * @param <I>
 */
public interface EntityDTO<I> extends Serializable {
    I getId();

    default void setId(I id) {

    }

    default Integer getVersion() {
        return null;
    }

    default void setVersion(Integer version) {

    }
}
