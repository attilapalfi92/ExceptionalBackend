package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Attila on 2015-06-11.
 */
public interface U2ECrud extends CrudRepository<Users2ExceptionsEntity, Long>, U2ECrudCustom {
}
