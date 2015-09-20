package com.attilapalf.exceptional.repositories.constants;

import com.attilapalf.exceptional.entities.ConstantsEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;

/**
 * Created by 212461305 on 2015.07.10..
 */
public class ConstantCrudImpl implements ConstantCrudCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int getMaxExceptionsPerUser() {
        return getIntValue("max_exceptions_per_user");
    }

    @Override
    public int getExceptionVersion() {
        return getIntValue("exception_version");
    }

    @Override
    public int getStartingPoint() {
        return getIntValue("starting_point");
    }

    @Override
    public int getClientCacheSize() {
        return getIntValue("client_cache_size");
    }

    @Override
    public void incrementExceptionVersion( ) {

    }

    private int getIntValue(String constantName) {
        ConstantsEntity constant = em.createQuery("SELECT c FROM ConstantsEntity c " +
                        "WHERE c.constantName = :constantName",
                ConstantsEntity.class)
                .setParameter("constantName", constantName)
                .getSingleResult();

        return constant.getIntValue().intValue();
    }
}
