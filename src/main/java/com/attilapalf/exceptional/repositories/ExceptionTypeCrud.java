package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.ExceptionTypesEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Attila on 2015-06-21.
 */
public interface ExceptionTypeCrud extends CrudRepository<ExceptionTypesEntity, Integer> {
}
