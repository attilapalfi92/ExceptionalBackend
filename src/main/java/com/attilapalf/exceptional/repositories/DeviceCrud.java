package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.DevicesEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by Attila on 2015-06-11.
 */
public interface DeviceCrud extends CrudRepository<DevicesEntity, BigInteger> {
}
