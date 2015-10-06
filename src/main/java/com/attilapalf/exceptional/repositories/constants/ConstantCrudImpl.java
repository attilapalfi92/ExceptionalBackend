package com.attilapalf.exceptional.repositories.constants;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.attilapalf.exceptional.entities.ConstantsEntity;

/**
 * Created by 212461305 on 2015.07.10..
 */
public class ConstantCrudImpl implements ConstantCrudCustom {
    public static final String MAX_EXCEPTIONS_PER_USER = "max_exceptions_per_user";
    public static final String EXCEPTION_VERSION = "exception_version";
    public static final String STARTING_POINT = "starting_point";
    public static final String CLIENT_CACHE_SIZE = "client_cache_size";
    public static final String CONSTANT_NAME = "constantName";

    @PersistenceContext
    private EntityManager em;

    @Override
    public int getMaxExceptionsPerUser( ) {
        return getIntValue( MAX_EXCEPTIONS_PER_USER );
    }

    @Override
    public int getExceptionVersion( ) {
        return getIntValue( EXCEPTION_VERSION );
    }

    @Override
    public int getStartingPoint( ) {
        return getIntValue( STARTING_POINT );
    }

    @Override
    public int getClientCacheSize( ) {
        return getIntValue( CLIENT_CACHE_SIZE );
    }

    @Override
    public void incrementExceptionVersion( ) {
        ConstantsEntity version = em.createQuery( "SELECT c FROM ConstantsEntity c " +
                        "WHERE c.constantName = :constantName",
                ConstantsEntity.class )
                .setParameter( CONSTANT_NAME, EXCEPTION_VERSION )
                .getSingleResult();
        version.setIntValue( version.getIntValue().add( BigInteger.ONE ) );
        em.persist( version );
    }

    private int getIntValue( String constantName ) {
        ConstantsEntity constant = em.createQuery( "SELECT c FROM ConstantsEntity c " +
                        "WHERE c.constantName = :constantName",
                ConstantsEntity.class )
                .setParameter( CONSTANT_NAME, constantName )
                .getSingleResult();

        return constant.getIntValue().intValue();
    }
}
