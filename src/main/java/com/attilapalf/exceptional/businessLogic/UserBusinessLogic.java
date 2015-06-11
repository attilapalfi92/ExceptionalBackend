package com.attilapalf.exceptional.businessLogic;

import com.attilapalf.exceptional.entities.DevicesEntity;
import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.DeviceCrud;
import com.attilapalf.exceptional.repositories.UserCrud;
import com.attilapalf.exceptional.repositories.U2ECrud;
import com.attilapalf.exceptional.wrappers.ExceptionWrapper;
import com.attilapalf.exceptional.wrappers.RegistrationRequestBody;
import com.attilapalf.exceptional.wrappers.RegistrationResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Attila on 2015-06-11.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class UserBusinessLogic {

    @Autowired
    private UserCrud userCrud;
    @Autowired
    private DeviceCrud deviceCrud;
    @Autowired
    private U2ECrud u2eCrud;

    @Transactional
    public RegistrationResponseBody registerUser(RegistrationRequestBody requestBody) {
        RegistrationResponseBody responseBody = new RegistrationResponseBody();
        UsersEntity user = userCrud.findOne(requestBody.getUserId());
        Collection<Long> facebookFriends = requestBody.getFriendsIds();

        // if user doesn't exist so he is a new user
        if (user == null) {
            // create and save it to database
            user = new UsersEntity();
            user.setUserId(requestBody.getUserId());
            user = userCrud.save(user);

            // Managing the new user
            manageNewUser(user, facebookFriends);

        }

        // if the user exists in the database
        else {

            // refreshing the user's friendships
            refreshUsersFriendships(user, facebookFriends);
        }

        // if this device of the user is not known yet
        if (!user.hasDevice(requestBody.getDeviceId())) {
            user = manageNewDevice(requestBody, user);
        }

        // now finding the user's exceptions
        Collection<Users2ExceptionsEntity> exceptions = u2eCrud.findExceptionsByUser(user);
        Collection<ExceptionWrapper> excWrappers = new ArrayList<>();

        for (Users2ExceptionsEntity e : exceptions) {
            excWrappers.add(new ExceptionWrapper(e));
        }

        responseBody.setMyExceptions(excWrappers);

        return responseBody;
    }


    private UsersEntity manageNewDevice(RegistrationRequestBody requestBody, UsersEntity user) {
        // create the device and save to database
        DevicesEntity device = new DevicesEntity();
        device.setDeviceId(requestBody.getDeviceId());
        device.setUser(user);
        device = deviceCrud.save(device);
        user.addDevice(device);
        return userCrud.save(user);
    }


    private void refreshUsersFriendships(UsersEntity user, Collection<Long> facebookFriends) {
        // these are the friends that the user possibly have
        Collection<UsersEntity> assumedFriends = userCrud.userIdsToUsersEntities(facebookFriends);

        // these are the friends that are already in the database
        Collection<UsersEntity> databaseFriends = userCrud.findExistingUsersFriends(user);

        // creating intersection of the two sets
        Collection<UsersEntity> intersection = new HashSet<>();
        intersection.addAll(assumedFriends);
        intersection.retainAll(databaseFriends);

        // getting the symmetric difference between the two sets
        assumedFriends.removeAll(intersection);
        databaseFriends.removeAll(intersection);

        // the friendships in the assumedFriends must be added to the database (they became friends since last check)
        userCrud.saveFriendships(user, assumedFriends);

        // the friendships in the databaseFriends must be removed from the database (they are not friends on facebook anymore)
        userCrud.deleteFriendships(user, databaseFriends);
    }


    private void manageNewUser(UsersEntity user, Collection<Long> facebookFriends) {
        // finding the friends of the user
        Collection<UsersEntity> userFriends = userCrud.userIdsToUsersEntities(facebookFriends);
        // saving the friends of the user to database
        userCrud.saveFriendships(user, userFriends);

        // TODO: notify friends about new user friend!

    }


    private Collection<Long> usersToIntegers (Collection<UsersEntity> users) {
        Collection<Long> result = new HashSet<>();
        for (UsersEntity u : users) {
            result.add(u.getUserId());
        }

        return result;
    }


}
