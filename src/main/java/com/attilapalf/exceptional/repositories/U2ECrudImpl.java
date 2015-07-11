package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.ExceptionTypesEntity;
import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.wrappers.ExceptionWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

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

    @Override
    public List<Users2ExceptionsEntity> findExceptionsBetweenIds(long idFrom, long idTo) {
        List<Users2ExceptionsEntity> result = em.createQuery(
                "SELECT u2e FROM Users2ExceptionsEntity u2e WHERE " +
                "u2e.u2EId > :idFrom AND u2e.u2EId < :idTo"
                , Users2ExceptionsEntity.class)
                .setParameter("idFrom", idFrom)
                .setParameter("idTo", idTo)
                .getResultList();

        return result;
    }

    @Override
    public List<Users2ExceptionsEntity> findExceptionsNotAmongIds(long userDbId, List<Long> knownIds, int maxExceptionsPerUser) {

        long idFrom = userDbId * maxExceptionsPerUser;
        List<Users2ExceptionsEntity> result = em.createQuery(
                "SELECT u2e FROM Users2ExceptionsEntity u2e " +
                "WHERE u2e.u2EId > :idFrom AND u2e.u2EId < :idTo " +
                "AND u2e.u2EId NOT IN :idList",
                Users2ExceptionsEntity.class)
                .setParameter("idFrom", idFrom)
                .setParameter("idTo", idFrom + maxExceptionsPerUser)
                .setParameter("idList", knownIds)
                .getResultList();

        return result;
    }

    @Override
    public Users2ExceptionsEntity saveNewException(ExceptionWrapper exceptionWrapper) {
        Users2ExceptionsEntity exception = new Users2ExceptionsEntity();
        exception.setCreationDate(new Timestamp(exceptionWrapper.getTimeInMillis()));
        exception.setExceptionType(em.getReference(ExceptionTypesEntity.class, exceptionWrapper.getExceptionTypeId()));
        exception.setFromUser(em.getReference(UsersEntity.class, exceptionWrapper.getFromWho()));
        exception.setToUser(em.getReference(UsersEntity.class, exceptionWrapper.getToWho()));
        exception.setLatitude(exceptionWrapper.getLatitude());
        exception.setLongitude(exceptionWrapper.getLongitude());

        em.persist(exception);

        return exception;
    }
}
