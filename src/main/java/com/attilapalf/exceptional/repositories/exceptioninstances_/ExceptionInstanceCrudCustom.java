package com.attilapalf.exceptional.repositories.exceptioninstances_;

import com.attilapalf.exceptional.entities.ExceptionInstancesEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.wrappers.ExceptionInstanceWrapper;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Attila on 2015-06-11.
 */
public interface ExceptionInstanceCrudCustom {

    List<ExceptionInstancesEntity> findLastExceptionsForUser(UsersEntity user);

    List<ExceptionInstancesEntity> findLastExceptionsNotAmongIds(UsersEntity user, List<BigInteger> knownIds);

    ExceptionInstancesEntity saveNewException(ExceptionInstanceWrapper exceptionInstanceWrapper);
}
