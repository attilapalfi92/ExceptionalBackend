package com.attilapalf.exceptional.repositories.constants;

import com.attilapalf.exceptional.entities.Constant;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by 212461305 on 2015.07.05..
 */
public interface ConstantCrud extends CrudRepository<Constant, Integer>, ConstantCrudCustom {
}
