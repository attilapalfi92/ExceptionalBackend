package com.attilapalf.exceptional.logic;

import com.attilapalf.exceptional.entities.DevicesEntity;
import com.attilapalf.exceptional.entities.ExceptionInstancesEntity;
import com.attilapalf.exceptional.entities.ExceptionTypesEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.beingvotedexceptiontypes.BeingVotedCrud;
import com.attilapalf.exceptional.repositories.exceptioninstances_.ExceptionInstanceCrud;
import com.attilapalf.exceptional.repositories.constants.ConstantCrud;
import com.attilapalf.exceptional.repositories.devices.DeviceCrud;
import com.attilapalf.exceptional.repositories.exceptiontypes.ExceptionTypeCrud;
import com.attilapalf.exceptional.repositories.friendships.FriendshipCrud;
import com.attilapalf.exceptional.repositories.users.UserCrud;
import com.attilapalf.exceptional.wrappers.ExceptionInstanceWrapper;
import com.attilapalf.exceptional.wrappers.AppStartRequestBody;
import com.attilapalf.exceptional.wrappers.AppStartResponseBody;
import com.attilapalf.exceptional.wrappers.ExceptionTypeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Attila on 2015-06-11.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class MainApplicationLogic {

    @Autowired
    private UserCrud userCrud;
    @Autowired
    private DeviceCrud deviceCrud;
    @Autowired
    private ExceptionInstanceCrud exceptionInstanceCrud;
    @Autowired
    private ConstantCrud constantCrud;
    @Autowired
    private FriendshipCrud friendshipCrud;
    @Autowired
    private ExceptionTypeCrud exceptionTypeCrud;
    @Autowired
    private BeingVotedCrud beingVotedCrud;

    private final String url = "https://android.googleapis.com/gcm/send";
    private final String apiKey = "AIzaSyCSwgwKHOuqBozM-JhhKYp6xnwFKs8xJrU";
    private final String projectNumber = "947608408958";

    @Transactional
    public AppStartResponseBody firstAppStart(AppStartRequestBody requestBody) {
        UsersEntity user = userCrud.findOne(requestBody.getUserFacebookId());
        if (user == null) {
            user = saveNewUser(requestBody);
        }
        updateUserName(requestBody, user);
        return handleExistingUserFirstStart(requestBody, user);
    }

    @Transactional
    public AppStartResponseBody regularAppStart(AppStartRequestBody requestBody) {
        UsersEntity user = userCrud.findOne(requestBody.getUserFacebookId());
        updateUserName(requestBody, user);
        removeInvalidFriendships(requestBody, user);
        saveNewFriends(requestBody, user);
        return createResponseForRegularAppStart(requestBody, user);
    }

    private UsersEntity saveNewUser(AppStartRequestBody requestBody) {
        UsersEntity user = new UsersEntity();
        user.setFacebookId(requestBody.getUserFacebookId());
        user.setPoints(constantCrud.getStartingPoint());
        return updateUserName(requestBody, user);
    }

    private UsersEntity updateUserName(AppStartRequestBody requestBody, UsersEntity user) {
        user.setFirstName(requestBody.getFirstName());
        user.setLastName(requestBody.getLastName());
        return userCrud.save(user);
    }

    private AppStartResponseBody handleExistingUserFirstStart(AppStartRequestBody requestBody, UsersEntity user) {
        saveDeviceForUser(requestBody, user);
        removeInvalidFriendships(requestBody, user);
        saveNewFriends(requestBody, user);
        return createResponseForFirstAppStart(user);
    }

    private void removeInvalidFriendships(AppStartRequestBody requestBody, UsersEntity user) {
        List<UsersEntity> existingFriends = friendshipCrud.findUsersExistingFriends(user);
        List<BigInteger> existingFriendIds = existingFriends.stream().map(UsersEntity::getFacebookId).collect(Collectors.toList());
        existingFriendIds.removeAll(requestBody.getFriendsFacebookIds());
        friendshipCrud.deleteFriendships(user, existingFriendIds);
    }

    private void saveNewFriends(AppStartRequestBody requestBody, UsersEntity user) {
        List<BigInteger> currentValidFriendIds = requestBody.getFriendsFacebookIds();
        userCrud.saveUsersIfNew(currentValidFriendIds);
        saveNewFriendships(user, currentValidFriendIds);
    }

    private AppStartResponseBody createResponseForFirstAppStart(UsersEntity user) {
        AppStartResponseBody responseBody = initResponseWithExceptions(user);
        addExceptionTypesToResponse(responseBody);
        addCommonDataToResponse(user, responseBody);
        return responseBody;
    }

    private void saveNewFriendships(UsersEntity user, List<BigInteger> currentValidFriendIds) {
        List<UsersEntity> existingFriends = friendshipCrud.findUsersExistingFriends(user);
        List<BigInteger> existingFriendIds = existingFriends.stream().map(UsersEntity::getFacebookId).collect(Collectors.toList());
        currentValidFriendIds.removeAll(existingFriendIds);
        friendshipCrud.saveFriendships(user, userCrud.userIdsToUsersEntities(currentValidFriendIds));
    }

    private AppStartResponseBody initResponseWithExceptions(UsersEntity user) {
        List<ExceptionInstancesEntity> exceptions = exceptionInstanceCrud.findLastExceptionsForUser(user);
        AppStartResponseBody responseBody = new AppStartResponseBody();
        responseBody.setMyExceptions(exceptions.stream().map(ExceptionInstanceWrapper::new).collect(Collectors.toList()));
        return responseBody;
    }

    private void addExceptionTypesToResponse(AppStartResponseBody responseBody) {
        List<ExceptionTypeWrapper> typeWrapperList = new ArrayList<>();
        exceptionTypeCrud.findAll().forEach(exceptionType ->
                typeWrapperList.add(new ExceptionTypeWrapper(exceptionType)));
        responseBody.setExceptionTypes(typeWrapperList);
        responseBody.setExceptionVersion(constantCrud.getExceptionVersion());
    }

    private void addCommonDataToResponse(UsersEntity user, AppStartResponseBody responseBody) {
        addVotedExceptionsToResponse(responseBody);
        responseBody.setPoints(user.getPoints());
        List<UsersEntity> friends = friendshipCrud.findUsersExistingFriends(user);
        responseBody.setFriendsPoints(friends.stream().collect(Collectors.toMap(UsersEntity::getFacebookId, UsersEntity::getPoints)));
    }

    private void addVotedExceptionsToResponse(AppStartResponseBody responseBody) {
        List<ExceptionTypeWrapper> typeWrapperList = new ArrayList<>();
        beingVotedCrud.findAll().forEach(exception ->
                typeWrapperList.add(new ExceptionTypeWrapper(exception)));
        responseBody.setBeingVotedTypes(typeWrapperList);
    }

    private void saveDeviceForUser(AppStartRequestBody requestBody, UsersEntity user) {
        DevicesEntity device = deviceCrud.findOne(requestBody.getDeviceId());
        if (device == null) {
            device = saveNewDevice(requestBody, user);
        }
        saveUserWithDevice(user, device);
    }

    private DevicesEntity saveNewDevice(AppStartRequestBody requestBody, UsersEntity user) {
        DevicesEntity device = new DevicesEntity();
        device.setDeviceId(requestBody.getDeviceId());
        device.setUser(user);
        device.setGcmId(requestBody.getGcmId());
        device.setDeviceName(requestBody.getDeviceName());
        return deviceCrud.save(device);
    }

    private void saveUserWithDevice(UsersEntity user, DevicesEntity device) {
        if (user.getDevices() == null) {
            user.setDevices(new ArrayList<>());
        }
        user.getDevices().add(device);
        userCrud.save(user);
    }

    private AppStartResponseBody createResponseForRegularAppStart(AppStartRequestBody requestBody, UsersEntity user) {
        AppStartResponseBody responseBody = initResponseWithFreshExceptions(requestBody, user);
        addFreshExceptionTypesToResponse(requestBody, responseBody);
        addCommonDataToResponse(user, responseBody);
        return responseBody;
    }

    private AppStartResponseBody initResponseWithFreshExceptions(AppStartRequestBody requestBody, UsersEntity user) {
        List<ExceptionInstancesEntity> exceptions = exceptionInstanceCrud
                .findLastExceptionsNotAmongIds(user, requestBody.getKnownExceptionIds());
        AppStartResponseBody response = new AppStartResponseBody();
        response.setMyExceptions(exceptions.stream().map(ExceptionInstanceWrapper::new).collect(Collectors.toList()));
        return response;
    }

    private void addFreshExceptionTypesToResponse(AppStartRequestBody requestBody, AppStartResponseBody responseBody) {
        int knownVersion = requestBody.getExceptionVersion();
        int currentVersion = constantCrud.getExceptionVersion();
        if (currentVersion > knownVersion) {
            List<ExceptionTypesEntity> exceptions = exceptionTypeCrud.findNewerTypesThanVersion(knownVersion);
            responseBody.setExceptionTypes(exceptions.stream().map(ExceptionTypeWrapper::new).collect(Collectors.toList()));
        }
        responseBody.setExceptionVersion(currentVersion);
    }
}
