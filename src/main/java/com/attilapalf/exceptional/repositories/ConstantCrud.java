package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.ConstantsEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by 212461305 on 2015.07.05..
 */
public interface ConstantCrud extends CrudRepository<ConstantsEntity, Integer>, ConstantCrudCustom {
}
