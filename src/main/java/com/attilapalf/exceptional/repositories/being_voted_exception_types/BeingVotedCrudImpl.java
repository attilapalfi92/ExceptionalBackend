package com.attilapalf.exceptional.repositories.being_voted_exception_types;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.attilapalf.exceptional.entities.BeingVotedExceptionTypeEntity;

/**
 * Created by palfi on 2015-08-20.
 */
public class BeingVotedCrudImpl implements BeingVotedCrudCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public BeingVotedExceptionTypeEntity findWinner( ) {
        return em.createQuery( "SELECT b from BeingVotedExceptionTypeEntity b " +
                "ORDER BY b.votes DESC", BeingVotedExceptionTypeEntity.class )
                .setMaxResults( 1 )
                .getSingleResult();
    }
}
