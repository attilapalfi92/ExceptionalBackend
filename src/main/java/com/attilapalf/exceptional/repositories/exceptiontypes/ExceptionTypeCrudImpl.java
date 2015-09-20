package com.attilapalf.exceptional.repositories.exceptiontypes;

import com.attilapalf.exceptional.entities.ExceptionTypesEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by palfi on 2015-08-21.
 */
public class ExceptionTypeCrudImpl implements ExceptionTypeCrudCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ExceptionTypesEntity> findNewerTypesThanVersion(int version) {
        return em.createQuery(
                "SELECT e FROM ExceptionTypesEntity e " +
                "WHERE e.version > :version",
                ExceptionTypesEntity.class)
                .setParameter("version", version)
                .getResultList();
    }
}
