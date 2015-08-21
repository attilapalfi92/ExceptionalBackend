package com.attilapalf.exceptional.repositories.exceptioninstances_;

import com.attilapalf.exceptional.entities.ExceptionInstancesEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by Attila on 2015-06-11.
 */
public interface ExceptionInstanceCrud extends CrudRepository<ExceptionInstancesEntity, BigInteger>, ExceptionInstanceCrudCustom {
}
