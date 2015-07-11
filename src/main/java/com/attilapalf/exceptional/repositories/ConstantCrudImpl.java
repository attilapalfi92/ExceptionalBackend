package com.attilapalf.exceptional.repositories;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by 212461305 on 2015.07.10..
 */
public class ConstantCrudImpl implements ConstantCrudCustom {

//    @PersistenceContext
//    private EntityManager em;

    @Autowired
    private ConstantCrud constantCrud;

    @Override
    public int findMaxExceptionsPerUser() {
        return (int) constantCrud.findOne(1).getConstantValueInt();
    }
}
