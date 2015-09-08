package com.attilapalf.exceptional.repositories.friendships;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;

import com.attilapalf.exceptional.entities.FriendshipsEntity;

/**
 * Created by palfi on 2015-08-20.
 */
public interface FriendshipCrud extends CrudRepository<FriendshipsEntity, BigInteger>, FriendshipCrudCustom {

}
