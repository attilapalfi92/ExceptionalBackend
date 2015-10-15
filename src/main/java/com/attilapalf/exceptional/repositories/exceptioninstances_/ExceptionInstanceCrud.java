package com.attilapalf.exceptional.repositories.exceptioninstances_;

import com.attilapalf.exceptional.entities.ExceptionInstance;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by Attila on 2015-06-11.
 */
public interface ExceptionInstanceCrud extends CrudRepository<ExceptionInstance, BigInteger>, ExceptionInstanceCrudCustom {
}
