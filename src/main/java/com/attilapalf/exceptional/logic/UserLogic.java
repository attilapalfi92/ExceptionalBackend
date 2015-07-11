package com.attilapalf.exceptional.logic;

import com.attilapalf.exceptional.entities.DevicesEntity;
import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.ConstantCrud;
import com.attilapalf.exceptional.repositories.DeviceCrud;
import com.attilapalf.exceptional.repositories.UserCrud;
import com.attilapalf.exceptional.repositories.U2ECrud;
import com.attilapalf.exceptional.wrappers.notifications.ExceptionNotification;
import com.attilapalf.exceptional.wrappers.ExceptionWrapper;
import com.attilapalf.exceptional.wrappers.AppStartRequestBody;
import com.attilapalf.exceptional.wrappers.AppStartResponseBody;
import com.attilapalf.exceptional.wrappers.notifications.FriendNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Attila on 2015-06-11.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class UserLogic {

    @Autowired
    private UserCrud userCrud;
    @Autowired
    private DeviceCrud deviceCrud;
    @Autowired
    private U2ECrud u2eCrud;
    @Autowired
    private ConstantCrud constantCrud;

    private final String url = "https://android.googleapis.com/gcm/send";
    private final String apiKey = "AIzaSyCSwgwKHOuqBozM-JhhKYp6xnwFKs8xJrU";
    private final String projectNumber = "947608408958";


    @Transactional
    public AppStartResponseBody appStart(AppStartRequestBody requestBody) {
        // TODO: by default on app start the user only gets the new exceptions since the last known from the client
        // TODO: if the user wants to refresh every exceptions, he has to pull down the list --> other method on backend

        int maxExceptionsPerUser = (int) constantCrud.findOne(1).getConstantValueInt();

        if (requestBody.getExceptionIds().size() != 0) {
            long fromId = requestBody.getExceptionIds().get(0);
            long toId = (fromId % maxExceptionsPerUser) + maxExceptionsPerUser;
            List<Users2ExceptionsEntity> newExceptions = u2eCrud.findExceptionsBetweenIds(fromId, toId);

            if (newExceptions.size() > 0) {
                List<ExceptionWrapper> exceptionWrappers = new ArrayList<>(newExceptions.size());
                for(Users2ExceptionsEntity u2e : newExceptions) {
                    exceptionWrappers.add(new ExceptionWrapper(u2e));
                }
                AppStartResponseBody responseBody = new AppStartResponseBody();

                // return with new exceptions, if there were any
                responseBody.setMyExceptions(exceptionWrappers);
            }
        }

        // else return with empty response
        return new AppStartResponseBody();
    }


    @Transactional
    public AppStartResponseBody firstAppStart(AppStartRequestBody requestBody) {
        AppStartResponseBody responseBody = new AppStartResponseBody();
        UsersEntity user = userCrud.findOne(requestBody.getUserId());
        Collection<Long> facebookFriends = requestBody.getFriendsIds();

        // if user doesn't exist so he is a new user
        if (user == null) {
            // create and save it to database
            user = new UsersEntity();
            user.setGcmId(requestBody.getRegId());
            user.setUserId(requestBody.getUserId());
            user = userCrud.save(user);

            // Managing the new user
            manageNewUser(user, facebookFriends);

        }

        // if the user exists in the database
        else {

            user.setGcmId(requestBody.getRegId());
            user = userCrud.save(user);
            // refreshing the user's friendships
            refreshUsersFriendships(user, facebookFriends);
        }

        // if this device of the user is not known yet
        if (!user.hasDevice(requestBody.getDeviceId())) {
            user = manageNewDevice(requestBody, user);
        }

        // now finding the user's exceptions
        Collection<Users2ExceptionsEntity> exceptions = u2eCrud.findExceptionsByUser(user);
        List<ExceptionWrapper> excWrappers = new ArrayList<>();

        for (Users2ExceptionsEntity e : exceptions) {
            excWrappers.add(new ExceptionWrapper(e));
        }

        responseBody.setMyExceptions(excWrappers);

        return responseBody;
    }


    private UsersEntity manageNewDevice(AppStartRequestBody requestBody, UsersEntity user) {
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
        List<String> friendRegIds = new ArrayList<>(userFriends.size());
        for (UsersEntity friend : userFriends) {
            friendRegIds.add(friend.getGcmId());
        }
        FriendNotification notification = new FriendNotification(user.getUserId(), friendRegIds);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "key=" + apiKey);

        HttpEntity<FriendNotification> requestData = new HttpEntity<>(notification, headers);

        RestTemplate restTemplate = new RestTemplate();

        // posting the data to recipient with gcm
        String gcmResponse = restTemplate.postForObject(url, requestData, String.class);
        int i = 0;
        i = i + 1;
    }


    private Collection<Long> usersToIntegers (Collection<UsersEntity> users) {
        Collection<Long> result = new HashSet<>();
        for (UsersEntity u : users) {
            result.add(u.getUserId());
        }

        return result;
    }


}
