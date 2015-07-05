package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.UsersEntity;

import java.util.Collection;

/**
 * Created by Attila on 2015-06-11.
 */
public interface UserCrudCustom {
    int deleteFriendships(UsersEntity user, Collection<UsersEntity> friends);
    int saveFriendships(UsersEntity user, Collection<UsersEntity> friends);

    /**
     * Finds the current friends of the user, based on the Friends table
     * @param user the user
     * @return friends of the user
     */
    Collection<UsersEntity> findExistingUsersFriends(UsersEntity user);

    /**
     * Finds the possible friends of the user, based on the Users table
     * @param facebookFriends facebook friends of the user
     * @return possible friends of the user
     */
    Collection<UsersEntity> userIdsToUsersEntities(Collection<Long> facebookFriends);

    /**
     * Calculates the min value of exception instance ids for this user
     * @param userId
     * @return
     */
    long getExceptionStartId(long userId);

    long getExceptionStartId(UsersEntity user);
}
