package com.attilapalf.exceptional.repositories.friendships;

import com.attilapalf.exceptional.entities.Friendship;
import com.attilapalf.exceptional.entities.User;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by palfi on 2015-08-20.
 */
public interface FriendshipCrudCustom {
    List<Friendship> findMyFriends(User user);

    int deleteFriendships(User user, List<BigInteger> friends);

    int saveFriendships(User user, List<User> friends);

    List<User> findUsersExistingFriends(User user);
}
