package com.attilapalf.exceptional.repositories.users;

import com.attilapalf.exceptional.entities.User;
import com.attilapalf.exceptional.repositories.constants.ConstantCrud;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Attila on 2015-06-11.
 */
public class UserCrudImpl implements UserCrudCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ConstantCrud constantCrud;

    /**
     * Finds the possible friends of the user, based on the Users table
     * @param facebookFriends facebook friends of the user
     * @return possible friends of the user
     */
    @Override
    public List<User> userIdsToUsersEntities(List<BigInteger> facebookFriends) {
        if (!facebookFriends.isEmpty()) {
            List<User> result = em.createQuery(
                    "SELECT u FROM User u WHERE " +
                            "u.facebookId IN :friends",
                    User.class)
                    .setParameter("friends", facebookFriends)
                    .getResultList();
            return result;
        }
        return new ArrayList<>();
    }

    @Override
    public void saveIfNew(BigInteger facebookId) {
        User user = em.find(User.class, facebookId);
        if (user == null) {
            user = new User();
            user.setFacebookId(facebookId);
            user.setPoints(constantCrud.getStartingPoint());
            em.persist(user);
        }
    }

    @Override
    public void saveUsersIfNew(List<BigInteger> facebookIds) {
        facebookIds.forEach(this::saveIfNew);
    }

    /**
     * Calculates the min value of exception instance ids for this user
     * @param userId
     * @return
     */
    @Override
    public BigInteger getExceptionStartId(BigInteger userId) {
        BigInteger maxExceptionsPerUser = constantCrud.findOne(1).getIntValue();
        User user = em.createQuery("SELECT u FROM User u " +
                "WHERE u.facebookId = :userId", User.class)
                .setParameter("userId", userId)
                .getSingleResult();
        return user.getDbId().multiply(maxExceptionsPerUser);
    }

    @Override
    public BigInteger getExceptionStartId(User user) {
        BigInteger maxExceptionsPerUser = constantCrud.findOne(1).getIntValue();
        return user.getDbId().multiply(maxExceptionsPerUser);
    }
}
