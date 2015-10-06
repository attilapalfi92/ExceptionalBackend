package com.attilapalf.exceptional.repositories.exceptioninstances_;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import com.attilapalf.exceptional.entities.ExceptionInstancesEntity;
import com.attilapalf.exceptional.entities.ExceptionTypesEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.messages.ExceptionInstanceWrapper;
import com.attilapalf.exceptional.repositories.constants.ConstantCrud;
import com.attilapalfi.exceptional.model.Question;

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
    public ExceptionInstancesEntity saveNewException( ExceptionInstanceWrapper instanceWrapper ) {
        ExceptionInstancesEntity exception = buildExceptionInstance( instanceWrapper );
        em.persist( exception );
        return exception;
    }

    @NotNull
    private ExceptionInstancesEntity buildExceptionInstance( ExceptionInstanceWrapper instanceWrapper ) {
        ExceptionInstancesEntity exception = new ExceptionInstancesEntity();
        exception.setDateTime( new Timestamp( instanceWrapper.getTimeInMillis() ) );
        exception.setType( em.getReference( ExceptionTypesEntity.class, instanceWrapper.getExceptionTypeId() ) );
        exception.setFromUser( em.getReference( UsersEntity.class, instanceWrapper.getFromWho() ) );
        exception.setToUser( em.getReference( UsersEntity.class, instanceWrapper.getToWho() ) );
        exception.setLatitude( instanceWrapper.getLatitude() );
        exception.setLongitude( instanceWrapper.getLongitude() );
        setQuestionForException( instanceWrapper, exception );
        return exception;
    }

    private void setQuestionForException( ExceptionInstanceWrapper instanceWrapper, ExceptionInstancesEntity exception ) {
        Question question = instanceWrapper.getQuestion();
        exception.setQuestionText( question.getHasQuestion() ? question.getText() : null );
        exception.setAnswered( false );
        exception.setHasQuestion( question.getHasQuestion() );
        exception.setYesIsCorrect( question.getYesIsCorrect() );
    }
}
