package com.attilapalf.exceptional.repositories.users;

import com.attilapalf.exceptional.entities.User;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Attila on 2015-06-11.
 */
public interface UserCrudCustom {

    List<User> userIdsToUsersEntities(List<BigInteger> facebookFriends);

    void saveIfNew(BigInteger facebookId);

    void saveUsersIfNew(List<BigInteger> facebookIds);

    BigInteger getExceptionStartId(BigInteger userId);

    BigInteger getExceptionStartId(User user);
}
