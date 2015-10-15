package com.attilapalf.exceptional.repositories.users;

import com.attilapalf.exceptional.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;


/**
 * Created by Attila on 2015-06-10.
 */
public interface UserCrud extends CrudRepository<User, BigInteger>, UserCrudCustom {
    Page<User> findAll(Pageable pageable);
}
