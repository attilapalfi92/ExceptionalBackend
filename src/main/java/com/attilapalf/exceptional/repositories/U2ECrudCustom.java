package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.wrappers.ExceptionWrapper;

import java.util.Collection;
import java.util.List;

/**
 * Created by Attila on 2015-06-11.
 */
public interface U2ECrudCustom {

    public Collection<Users2ExceptionsEntity> findExceptionsByUser(UsersEntity user);

    public List<Users2ExceptionsEntity> findExceptionsBetweenIds(long idFrom, long idTo);

    public String saveNewException(ExceptionWrapper exceptionWrapper);
}
