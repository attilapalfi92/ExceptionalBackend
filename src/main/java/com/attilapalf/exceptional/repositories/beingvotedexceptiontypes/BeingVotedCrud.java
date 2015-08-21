package com.attilapalf.exceptional.repositories.beingvotedexceptiontypes;

import com.attilapalf.exceptional.entities.BeingVotedExceptionTypeEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by palfi on 2015-08-20.
 */
public interface BeingVotedCrud extends CrudRepository<BeingVotedExceptionTypeEntity, Integer>, BeingVotedCrudCustom {
}
