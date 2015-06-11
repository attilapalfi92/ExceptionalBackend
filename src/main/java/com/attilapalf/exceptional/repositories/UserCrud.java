package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;



/**
 * Created by Attila on 2015-06-10.
 */
public interface UserCrud extends CrudRepository<UsersEntity, Long>, UserCrudCustom {

    Page<UsersEntity> findAll(Pageable pageable);
}
