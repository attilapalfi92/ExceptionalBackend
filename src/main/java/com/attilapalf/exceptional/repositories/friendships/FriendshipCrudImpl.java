package com.attilapalf.exceptional.repositories.friendships;

import com.attilapalf.exceptional.entities.FriendshipsEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Created by palfi on 2015-08-20.
 */
public class FriendshipCrudImpl implements FriendshipCrudCustom {
    @Autowired
    private EntityManager em;

    @Override
    public List<FriendshipsEntity> findMyFriends(UsersEntity user) {
        return em.createQuery("SELECT f FROM FriendshipsEntity f WHERE " +
                "f.user1 = :user1 OR f.user2 = :user2", FriendshipsEntity.class)
                .setParameter("user1", user)
                .setParameter("user2", user)
                .getResultList();
    }

    @Override
    public int deleteFriendships(UsersEntity user, List<BigInteger> friends) {
        if (!friends.isEmpty()) {
            int deletedCount = em.createQuery("DELETE FROM FriendshipsEntity f WHERE " +
                    "(f.user1 = :user AND f.user2.facebookId IN :friends) " +
                    "OR (f.user1 = :user AND f.user2.facebookId IN :friends)")
                    .setParameter("user", user)
                    .setParameter("friends", friends)
                    .executeUpdate();

            return deletedCount;
        }
        return 0;
    }


    @Override
    public int saveFriendships(UsersEntity user, List<UsersEntity> friends) {
        int i = 0;
        for(UsersEntity friend : friends) {
            em.persist(new FriendshipsEntity(user, friend));
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
    public List<UsersEntity> findUsersExistingFriends(UsersEntity user) {
        List<UsersEntity> result = em.createQuery(
                "SELECT u FROM UsersEntity u WHERE " +
                        "u IN (SELECT f.user1 FROM FriendshipsEntity f WHERE f.user2 = :user)" +
                        "OR u IN (SELECT f.user2 FROM FriendshipsEntity f WHERE f.user1 = :user)",
                UsersEntity.class)
                .setParameter("user", user)
                .getResultList();

        return result;
    }
}
