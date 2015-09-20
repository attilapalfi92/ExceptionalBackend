package com.attilapalf.exceptional.repositories.exceptioninstances_;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.attilapalf.exceptional.entities.ExceptionInstancesEntity;
import com.attilapalf.exceptional.entities.ExceptionTypesEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.messages.ExceptionInstanceWrapper;
import com.attilapalf.exceptional.repositories.constants.ConstantCrud;

/**
 * Created by Attila on 2015-06-11.
 */
public class ExceptionInstanceCrudImpl implements ExceptionInstanceCrudCustom {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ConstantCrud constantCrud;

    @Override
    public List<ExceptionInstancesEntity> findLastExceptionsForUser( UsersEntity user ) {
        List<ExceptionInstancesEntity> result = em.createQuery(
                "SELECT e FROM ExceptionInstancesEntity e " +
                        "WHERE e.toUser = :user OR e.fromUser = :user " +
                        "ORDER BY e.id DESC",
                ExceptionInstancesEntity.class )
                .setParameter( "user", user )
                .setMaxResults( constantCrud.getClientCacheSize() )
                .getResultList();

        return result;
    }

    @Override
    public List<ExceptionInstancesEntity> findLastExceptionsNotAmongIds( UsersEntity user, List<BigInteger> knownIds ) {
        if ( knownIds.isEmpty() ) {
            knownIds.add( new BigInteger( "0" ) );
        }
        List<ExceptionInstancesEntity> result = em.createQuery(
                "SELECT e FROM ExceptionInstancesEntity e " +
                        "WHERE (e.toUser = :user OR e.fromUser = :user) " +
                        "AND e.id NOT IN :idList " +
                        "ORDER BY e.id DESC",
                ExceptionInstancesEntity.class )
                .setParameter( "user", user )
                .setParameter( "idList", knownIds )
                .setMaxResults( constantCrud.getClientCacheSize() - knownIds.size() )
                .getResultList();

        return result;
    }

    @Override
    public ExceptionInstancesEntity saveNewException( ExceptionInstanceWrapper exceptionInstanceWrapper ) {
        ExceptionInstancesEntity exception = new ExceptionInstancesEntity();
        exception.setDateTime( new Timestamp( exceptionInstanceWrapper.getTimeInMillis() ) );
        exception.setType( em.getReference( ExceptionTypesEntity.class, exceptionInstanceWrapper.getExceptionTypeId() ) );
        exception.setFromUser( em.getReference( UsersEntity.class, exceptionInstanceWrapper.getFromWho() ) );
        exception.setToUser( em.getReference( UsersEntity.class, exceptionInstanceWrapper.getToWho() ) );
        exception.setLatitude( exceptionInstanceWrapper.getLatitude() );
        exception.setLongitude( exceptionInstanceWrapper.getLongitude() );
        em.persist( exception );
        return exception;
    }
}
