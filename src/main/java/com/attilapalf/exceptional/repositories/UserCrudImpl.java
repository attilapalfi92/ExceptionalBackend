package com.attilapalf.exceptional.repositories;

import com.attilapalf.exceptional.entities.FriendsEntity;
import com.attilapalf.exceptional.entities.UsersEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Attila on 2015-06-11.
 */
public class UserCrudImpl implements UserCrudCustom {
    @PersistenceContext
    EntityManager em;

    public Collection<FriendsEntity> findMyFriends(UsersEntity user) {
        return em.createQuery("SELECT f FROM FriendsEntity f WHERE " +
                "f.user1 = :user1 OR f.user2 = :user2", FriendsEntity.class)
                .setParameter("user1", user)
                .setParameter("user2", user)
                .getResultList();
    }

    @Override
    public int deleteFriendships(UsersEntity user, Collection<UsersEntity> friends) {

        if (!friends.isEmpty()) {
            int deletedCount = em.createQuery("DELETE FROM FriendsEntity f WHERE " +
                    "(f.user1 = :user AND f.user2 IN :friends) " +
                    "OR (f.user2 = :user AND f.user1 IN :friends)")
                    .setParameter("user", user)
                    .setParameter("friends", friends)
                    .executeUpdate();

            return deletedCount;
        }

        return 0;
    }


    @Override
    public int saveFriendships(UsersEntity user, Collection<UsersEntity> friends) {
        int i = 0;
        for(UsersEntity friend : friends) {
            em.persist(new FriendsEntity(user, friend));
            i++;
        }

        return i;
    }


    /**
     * Finds the current friends of the user, based on the Friends table
     * @param user the user
     * @return friends of the user
     */
    @Override
    public Collection<UsersEntity> findExistingUsersFriends(UsersEntity user) {
        Collection<UsersEntity> result = em.createQuery(
                "SELECT u FROM UsersEntity u WHERE " +
                "u IN (SELECT f.user1 FROM FriendsEntity f WHERE f.user2 = :user)" +
                "OR u IN (SELECT f.user2 FROM FriendsEntity f WHERE f.user1 = :user)",
                UsersEntity.class)
                .setParameter("user", user)
                .getResultList();

        return result;
    }


    /**
     * Finds the possible friends of the user, based on the Users table
     * @param facebookFriends facebook friends of the user
     * @return possible friends of the user
     */
    @Override
    public Collection<UsersEntity> userIdsToUsersEntities(Collection<Long> facebookFriends) {
        if (!facebookFriends.isEmpty()) {
            Collection<UsersEntity> result = em.createQuery(
                    "SELECT u FROM UsersEntity u WHERE " +
                            "u.userId IN :friends",
                    UsersEntity.class)
                    .setParameter("friends", facebookFriends)
                    .getResultList();

            return result;
        }

        return new HashSet<>();
    }
}
