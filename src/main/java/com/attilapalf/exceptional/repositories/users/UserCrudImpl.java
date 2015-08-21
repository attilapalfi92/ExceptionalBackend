package com.attilapalf.exceptional.repositories.users;

import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.constants.ConstantCrud;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Attila on 2015-06-11.
 */
public class UserCrudImpl implements UserCrudCustom {
    @PersistenceContext
    EntityManager em;

    @Autowired
    ConstantCrud constantCrud;

    /**
     * Finds the possible friends of the user, based on the Users table
     * @param facebookFriends facebook friends of the user
     * @return possible friends of the user
     */
    @Override
    public List<UsersEntity> userIdsToUsersEntities(List<BigInteger> facebookFriends) {
        if (!facebookFriends.isEmpty()) {
            List<UsersEntity> result = em.createQuery(
                    "SELECT u FROM UsersEntity u WHERE " +
                            "u.facebookId IN :friends",
                    UsersEntity.class)
                    .setParameter("friends", facebookFriends)
                    .getResultList();
            return result;
        }
        return new ArrayList<>();
    }

    @Override
    public void saveIfNew(BigInteger facebookId) {
        UsersEntity user = em.find(UsersEntity.class, facebookId);
        if (user == null) {
            user = new UsersEntity();
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
        UsersEntity user = em.createQuery("SELECT u FROM UsersEntity u " +
                "WHERE u.facebookId = :userId", UsersEntity.class)
                .setParameter("userId", userId)
                .getSingleResult();
        return user.getDbId().multiply(maxExceptionsPerUser);
    }

    @Override
    public BigInteger getExceptionStartId(UsersEntity user) {
        BigInteger maxExceptionsPerUser = constantCrud.findOne(1).getIntValue();
        return user.getDbId().multiply(maxExceptionsPerUser);
    }
}
