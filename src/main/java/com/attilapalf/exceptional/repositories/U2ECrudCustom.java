package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;
import com.attilapalf.exceptional.entities.UsersEntity;

import java.util.Collection;

/**
 * Created by Attila on 2015-06-11.
 */
public interface U2ECrudCustom {

    public Collection<Users2ExceptionsEntity> findExceptionsByUser(UsersEntity user);
}
