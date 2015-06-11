package com.attilapalf.exceptional.businessLogic;

import com.attilapalf.exceptional.entities.DevicesEntity;
import com.attilapalf.exceptional.entities.FriendsEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.DeviceCrud;
import com.attilapalf.exceptional.repositories.FriendsCrud;
import com.attilapalf.exceptional.repositories.UserCrud;
import com.attilapalf.exceptional.repositories.UserRepository;
import com.attilapalf.exceptional.wrappers.RegistrationRequestBody;
import com.attilapalf.exceptional.wrappers.RegistrationResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Attila on 2015-06-11.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class UserBusinessLogic {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCrud userCrud;
    @Autowired
    private DeviceCrud deviceCrud;
    @Autowired
    private FriendsCrud friendsCrud;

    @Transactional
    public RegistrationResponseBody registerUser(RegistrationRequestBody requestBody) {
        UsersEntity user = userCrud.findOne(requestBody.getUserId());
        // if user doesn't exist
        if (user == null) {
            // create and save it to database
            user = new UsersEntity();
            user.setUserId(requestBody.getUserId());
            user = userCrud.save(user);
        }
        // if this device of the user is not known yet
        if (!user.hasDevice(requestBody.getDeviceId())) {
            // create the device and save to database
            DevicesEntity device = new DevicesEntity();
            device.setDeviceId(requestBody.getDeviceId());
            device.setUser(user);
            device = deviceCrud.save(device);
            user.addDevice(device);
            user = userCrud.save(user);
        }

        // now finding the user's friends

        return null;
    }

    private List<BigInteger> checkUserFriends(List<BigInteger> facebookFriends) {
        List<BigInteger> playingFriends = new ArrayList<>();

        Iterable<FriendsEntity> allFriendships = friendsCrud.findAll();
        for (FriendsEntity dbF : allFriendships) {
            for (BigInteger fbF : facebookFriends) {

                //
                if (dbF.getUser1().getUserId().equals(fbF)) {

                }
            }
        }

        return null;
    }
}
