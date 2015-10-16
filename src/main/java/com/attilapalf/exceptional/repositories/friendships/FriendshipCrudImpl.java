package com.attilapalf.exceptional.repositories.friendships;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.attilapalf.exceptional.entities.Friendship;
import com.attilapalf.exceptional.entities.User;

/**
 * Created by palfi on 2015-08-20.
 */
public class FriendshipCrudImpl implements FriendshipCrudCustom {
    @Autowired
    private EntityManager em;

    @Override
    public List<Friendship> findMyFriends( User user ) {
        return em.createQuery( "SELECT f FROM Friendship f WHERE " +
                "f.user1 = :user1 OR f.user2 = :user2", Friendship.class )
                .setParameter( "user1", user )
                .setParameter( "user2", user )
                .getResultList();
    }

    @Override
    public int deleteFriendships( User user, List<BigInteger> friends ) {
        if ( !friends.isEmpty() ) {

            friends.forEach( friendId ->
                            em.createQuery( "DELETE FROM Friendship f WHERE " +
                                    "(f.user1 = :user AND f.user2.facebookId = :friendId) " +
                                    "OR  (f.user2 = :user AND f.user1.facebookId = :friendId)" )
                                    .setParameter( "user", user )
                                    .setParameter( "friendId", friendId )
                                    .executeUpdate()
            );

            return friends.size();
        }
        return 0;
    }


    @Override
    public int saveFriendships( User user, List<User> friends ) {
        int i = 0;
        for ( User friend : friends ) {
            em.persist( new Friendship( user, friend ) );
            i++;
        }

        return i;
    }


    /**
     * Finds the current friends of the user, based on the Friends table
     *
     * @param user the user
     * @return friends of the user
     */
    @Override
    public List<User> findUsersExistingFriends( User user ) {
        List<User> result = em.createQuery(
                "SELECT u FROM User u WHERE " +
                        "u IN (SELECT f.user1 FROM Friendship f WHERE f.user2 = :user)" +
                        "OR u IN (SELECT f.user2 FROM Friendship f WHERE f.user1 = :user)",
                User.class )
                .setParameter( "user", user )
                .getResultList();

        return result;
    }
}
