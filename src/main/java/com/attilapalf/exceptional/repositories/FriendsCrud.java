package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.FriendsEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by Attila on 2015-06-11.
 */
public interface FriendsCrud extends CrudRepository<FriendsEntity, BigInteger> {
}
