package com.attilapalf.exceptional.repositories.exceptiontypes;

import com.attilapalf.exceptional.entities.ExceptionType;

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
    public List<ExceptionType> findNewerTypesThanVersion(int version) {
        return em.createQuery(
                "SELECT e FROM ExceptionType e " +
                "WHERE e.version > :version",
                ExceptionType.class)
                .setParameter("version", version)
                .getResultList();
    }
}
