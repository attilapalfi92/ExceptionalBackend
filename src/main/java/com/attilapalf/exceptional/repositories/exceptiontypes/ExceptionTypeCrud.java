package com.attilapalf.exceptional.repositories.exceptiontypes;

import com.attilapalf.exceptional.entities.ExceptionType;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Attila on 2015-06-21.
 */
public interface ExceptionTypeCrud extends CrudRepository<ExceptionType, Integer>, ExceptionTypeCrudCustom {
}
