package com.attilapalf.exceptional.repositories.exceptioninstances_;

import java.math.BigInteger;
import java.util.List;

import com.attilapalf.exceptional.entities.ExceptionInstance;
import com.attilapalf.exceptional.entities.ExceptionType;
import com.attilapalf.exceptional.entities.User;
import com.attilapalf.exceptional.messages.ExceptionInstanceWrapper;

/**
 * Created by Attila on 2015-06-11.
 */
public interface ExceptionInstanceCrudCustom {

    List<ExceptionInstance> findLastExceptionsForUser( User user );

    List<ExceptionInstance> findLastExceptionsNotAmongIds( User user, List<BigInteger> knownIds );

    ExceptionInstance saveNewException( ExceptionInstanceWrapper exceptionInstanceWrapper );

    long getCountForType( ExceptionType exceptionType );
}
