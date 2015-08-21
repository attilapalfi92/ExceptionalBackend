package com.attilapalf.exceptional.repositories.exceptiontypes;

import com.attilapalf.exceptional.entities.ExceptionTypesEntity;

import java.util.List;

/**
 * Created by palfi on 2015-08-21.
 */
public interface ExceptionTypeCrudCustom {
    List<ExceptionTypesEntity> findNewerTypesThanVersion(int version);
}
