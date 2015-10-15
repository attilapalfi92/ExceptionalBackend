package com.attilapalf.exceptional.repositories.being_voted_exception_types;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.attilapalf.exceptional.entities.BeingVotedExceptionType;

/**
 * Created by palfi on 2015-08-20.
 */
public class BeingVotedCrudImpl implements BeingVotedCrudCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public BeingVotedExceptionType findWinner( ) {
        return em.createQuery( "SELECT b from BeingVotedExceptionType b " +
                "ORDER BY b.votes DESC", BeingVotedExceptionType.class )
                .setMaxResults( 1 )
                .getSingleResult();
    }
}
