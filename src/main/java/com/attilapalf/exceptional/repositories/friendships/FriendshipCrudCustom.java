package com.attilapalf.exceptional.repositories.friendships;

import com.attilapalf.exceptional.entities.FriendshipsEntity;
import com.attilapalf.exceptional.entities.UsersEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by palfi on 2015-08-20.
 */
public interface FriendshipCrudCustom {
    List<FriendshipsEntity> findMyFriends(UsersEntity user);

    int deleteFriendships(UsersEntity user, List<BigInteger> friends);

    int saveFriendships(UsersEntity user, List<UsersEntity> friends);

    List<UsersEntity> findUsersExistingFriends(UsersEntity user);
}
