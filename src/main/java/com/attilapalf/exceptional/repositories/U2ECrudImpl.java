package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;
import com.attilapalf.exceptional.entities.UsersEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * Created by Attila on 2015-06-11.
 */
public class U2ECrudImpl implements U2ECrudCustom {

    @PersistenceContext
    EntityManager em;

    @Override
    public Collection<Users2ExceptionsEntity> findExceptionsByUser(UsersEntity user) {
        Collection<Users2ExceptionsEntity> result = em.createQuery(
                "SELECT u2e FROM Users2ExceptionsEntity u2e WHERE " +
                "u2e.toUser = :user",
                Users2ExceptionsEntity.class)
                .setParameter("user", user)
                .getResultList();

        return result;
    }
}
