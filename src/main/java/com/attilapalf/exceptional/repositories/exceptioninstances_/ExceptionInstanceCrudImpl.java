package com.attilapalf.exceptional.repositories.exceptioninstances_;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import com.attilapalf.exceptional.entities.ExceptionInstance;
import com.attilapalf.exceptional.entities.ExceptionType;
import com.attilapalf.exceptional.entities.User;
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
    public List<ExceptionInstance> findLastExceptionsForUser( User user ) {
        List<ExceptionInstance> result = em.createQuery(
                "SELECT e FROM ExceptionInstance e " +
                        "WHERE e.toUser = :user OR e.fromUser = :user " +
                        "ORDER BY e.id DESC",
                ExceptionInstance.class )
                .setParameter( "user", user )
                .setMaxResults( constantCrud.getClientCacheSize() )
                .getResultList();

        return result;
    }

    @Override
    public List<ExceptionInstance> findLastExceptionsNotAmongIds( User user, List<BigInteger> knownIds ) {
        if ( knownIds.isEmpty() ) {
            knownIds.add( new BigInteger( "0" ) );
        }
        List<ExceptionInstance> result = em.createQuery(
                "SELECT e FROM ExceptionInstance e " +
                        "WHERE (e.toUser = :user OR e.fromUser = :user) " +
                        "AND e.id NOT IN :idList " +
                        "ORDER BY e.id DESC",
                ExceptionInstance.class )
                .setParameter( "user", user )
                .setParameter( "idList", knownIds )
                .setMaxResults( constantCrud.getClientCacheSize() - knownIds.size() )
                .getResultList();

        return result;
    }

    @Override
    public ExceptionInstance saveNewException( ExceptionInstanceWrapper instanceWrapper ) {
        ExceptionInstance exception = buildExceptionInstance( instanceWrapper );
        em.persist( exception );
        return exception;
    }

    @Override
    public long getCountForType( ExceptionType exceptionType ) {
        return em.createQuery( "SELECT COUNT (e.id) FROM ExceptionInstance  e " +
                "WHERE e.type = :type", Long.class )
                .setParameter( "type", exceptionType )
                .getSingleResult();
    }

    @NotNull
    private ExceptionInstance buildExceptionInstance( ExceptionInstanceWrapper instanceWrapper ) {
        ExceptionInstance exception = new ExceptionInstance();
        exception.setDateTime( new Timestamp( instanceWrapper.getTimeInMillis() ) );
        exception.setType( em.getReference( ExceptionType.class, instanceWrapper.getExceptionTypeId() ) );
        exception.setFromUser( em.getReference( User.class, instanceWrapper.getFromWho() ) );
        exception.setToUser( em.getReference( User.class, instanceWrapper.getToWho() ) );
        exception.setLatitude( instanceWrapper.getLatitude() );
        exception.setLongitude( instanceWrapper.getLongitude() );
        exception.setPointsForSender( instanceWrapper.getPointsForSender() );
        exception.setPointsForReceiver( instanceWrapper.getPointsForReceiver() );
        exception.setCity( instanceWrapper.getCity() );
        setQuestionForException( instanceWrapper, exception );
        return exception;
    }

    private void setQuestionForException( ExceptionInstanceWrapper instanceWrapper, ExceptionInstance exception ) {
        Question question = instanceWrapper.getQuestion();
        exception.setQuestionText( question.getHasQuestion() ? question.getText() : null );
        exception.setAnswered( false );
        exception.setHasQuestion( question.getHasQuestion() );
        exception.setYesIsCorrect( question.getYesIsCorrect() );
    }
}
