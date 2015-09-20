package com.attilapalf.exceptional.repositories.being_voted_exception_types;

import com.attilapalf.exceptional.entities.BeingVotedExceptionTypeEntity;

/**
 * Created by palfi on 2015-08-20.
 */
public interface BeingVotedCrudCustom {
    BeingVotedExceptionTypeEntity findWinner();
}
