package com.kumar.springbootlearning.mvc.remote;

import com.kumar.springbootlearning.data.filter.FilterPredicate;
import com.kumar.springbootlearning.data.model.BaseEntity;
import com.kumar.springbootlearning.data.model.EntityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RemoteService<I, D extends EntityDTO<I>, E extends BaseEntity<I>> {

    Page<D> findAll(FilterPredicate filter, Pageable pageable);

    Page<D> findAll(Pageable pageable);

    Optional<D> findOne(I id);

    Class<E> getEntityClass();

    String getTargetService();
}