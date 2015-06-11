package com.attilapalf.exceptional.repositories;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Attila on 2015-06-11.
 */
@Repository
public class UserRepository {

    @PersistenceContext
    EntityManager entityManager;


}
