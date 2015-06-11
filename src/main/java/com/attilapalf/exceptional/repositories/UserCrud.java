package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;


/**
 * Created by Attila on 2015-06-10.
 */
public interface UserCrud extends CrudRepository<UsersEntity, BigInteger> {

    Page<UsersEntity> findAll(Pageable pageable);
}
