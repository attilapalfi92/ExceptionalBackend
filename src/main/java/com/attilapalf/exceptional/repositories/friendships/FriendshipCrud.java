package com.attilapalf.exceptional.repositories.friendships;

import com.attilapalf.exceptional.entities.FriendshipsEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by palfi on 2015-08-20.
 */
public interface FriendshipCrud extends CrudRepository<FriendshipsEntity, BigInteger>, FriendshipCrudCustom {

}
