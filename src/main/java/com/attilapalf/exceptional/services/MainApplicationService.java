package com.attilapalf.exceptional.services;

import com.attilapalf.exceptional.entities.DevicesEntity;
import com.attilapalf.exceptional.entities.ExceptionInstancesEntity;
import com.attilapalf.exceptional.entities.ExceptionTypesEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.being_voted_exception_types.BeingVotedCrud;
import com.attilapalf.exceptional.repositories.exceptioninstances_.ExceptionInstanceCrud;
import com.attilapalf.exceptional.repositories.constants.ConstantCrud;
import com.attilapalf.exceptional.repositories.devices.DeviceCrud;
import com.attilapalf.exceptional.repositories.exceptiontypes.ExceptionTypeCrud;
import com.attilapalf.exceptional.repositories.friendships.FriendshipCrud;
import com.attilapalf.exceptional.repositories.users.UserCrud;
import com.attilapalf.exceptional.messages.*;
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
public class MainApplicationService {
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
    @Autowired
    private GcmMessageService gcmMessageService;

    @Transactional
    public AppStartResponse firstAppStart(AppStartRequest requestBody) {
        UsersEntity user = userCrud.findOne(requestBody.getUserFacebookId());
        boolean newUser = false;
        if (user == null) {
            newUser = true;
            user = saveNewUser(requestBody);
        }
        updateUserName(requestBody, user);
        return handleUserFirstStart(requestBody, user, newUser);
    }

    @Transactional
    public AppStartResponse regularAppStart(AppStartRequest requestBody) {
        UsersEntity user = userCrud.findOne(requestBody.getUserFacebookId());
        updateUserName(requestBody, user);
        removeInvalidFriendships(requestBody, user);
        saveNewFriends(requestBody, user);
        return createResponseForRegularAppStart(requestBody, user);
    }

    private UsersEntity saveNewUser(AppStartRequest requestBody) {
        UsersEntity user = new UsersEntity();
        user.setFacebookId(requestBody.getUserFacebookId());
        user.setPoints(constantCrud.getStartingPoint());
        return updateUserName(requestBody, user);
    }

    private UsersEntity updateUserName(AppStartRequest requestBody, UsersEntity user) {
        user.setFirstName(requestBody.getFirstName());
        user.setLastName(requestBody.getLastName());
        return userCrud.save(user);
    }

    private AppStartResponse handleUserFirstStart(AppStartRequest requestBody, UsersEntity user, boolean newUser) {
        saveDeviceForUser(requestBody, user);
        removeInvalidFriendships(requestBody, user);
        List<UsersEntity> friends = saveNewFriends(requestBody, user);
        if (newUser) {
            gcmMessageService.sendFriendNotification(user, friends);
        }
        return createResponseForFirstAppStart(user);
    }

    private void removeInvalidFriendships(AppStartRequest requestBody, UsersEntity user) {
        List<UsersEntity> existingFriends = friendshipCrud.findUsersExistingFriends(user);
        List<BigInteger> existingFriendIds = existingFriends.stream().map(UsersEntity::getFacebookId).collect(Collectors.toList());
        existingFriendIds.removeAll(requestBody.getFriendsFacebookIds());
        friendshipCrud.deleteFriendships(user, existingFriendIds);
    }

    private List<UsersEntity> saveNewFriends(AppStartRequest requestBody, UsersEntity user) {
        List<BigInteger> currentValidFriendIds = requestBody.getFriendsFacebookIds();
        userCrud.saveUsersIfNew(currentValidFriendIds);
        return saveNewFriendships(user, currentValidFriendIds);
    }

    private AppStartResponse createResponseForFirstAppStart(UsersEntity user) {
        AppStartResponse responseBody = initResponseWithExceptions(user);
        addExceptionTypesToResponse(responseBody);
        addCommonDataToResponse(user, responseBody);
        return responseBody;
    }

    private List<UsersEntity> saveNewFriendships(UsersEntity user, List<BigInteger> currentValidFriendIds) {
        List<UsersEntity> existingFriends = friendshipCrud.findUsersExistingFriends(user);
        List<BigInteger> existingFriendIds = existingFriends.stream().map(UsersEntity::getFacebookId).collect(Collectors.toList());
        currentValidFriendIds.removeAll(existingFriendIds);
        friendshipCrud.saveFriendships(user, userCrud.userIdsToUsersEntities(currentValidFriendIds));
        return existingFriends;
    }

    private AppStartResponse initResponseWithExceptions(UsersEntity user) {
        List<ExceptionInstancesEntity> exceptions = exceptionInstanceCrud.findLastExceptionsForUser(user);
        AppStartResponse responseBody = new AppStartResponse();
        responseBody.setMyExceptions(exceptions.stream().map(ExceptionInstanceWrapper::new).collect(Collectors.toList()));
        return responseBody;
    }

    private void addExceptionTypesToResponse(AppStartResponse responseBody) {
        List<ExceptionTypeWrapper> typeWrapperList = new ArrayList<>();
        exceptionTypeCrud.findAll().forEach(exceptionType ->
                typeWrapperList.add(new ExceptionTypeWrapper(exceptionType)));
        responseBody.setExceptionTypes(typeWrapperList);
        responseBody.setExceptionVersion(constantCrud.getExceptionVersion());
    }

    private void addCommonDataToResponse(UsersEntity user, AppStartResponse responseBody) {
        addVotedExceptionsToResponse(responseBody);
        addVoteMetadataToResponse(user, responseBody);
        responseBody.setPoints(user.getPoints());
        List<UsersEntity> friends = friendshipCrud.findUsersExistingFriends(user);
        responseBody.setFriendsPoints(friends.stream().collect(Collectors.toMap(UsersEntity::getFacebookId, UsersEntity::getPoints)));
    }

    private void addVoteMetadataToResponse(UsersEntity user, AppStartResponse responseBody) {
        responseBody.setVotedThisWeek( user.getVotedForException() != null ? true : false );
        responseBody.setSubmittedThisWeek( user.getMySubmissionForVote() != null ? true : false );
    }

    private void addVotedExceptionsToResponse(AppStartResponse responseBody) {
        List<ExceptionTypeWrapper> typeWrapperList = new ArrayList<>();
        beingVotedCrud.findAll().forEach(exception ->
                typeWrapperList.add(new ExceptionTypeWrapper(exception)));
        responseBody.setBeingVotedTypes(typeWrapperList);
    }

    private void saveDeviceForUser(AppStartRequest requestBody, UsersEntity user) {
        DevicesEntity device = deviceCrud.findOne(requestBody.getDeviceId());
        if (device == null) {
            device = saveNewDevice(requestBody, user);
        }
        saveUserWithDevice(user, device);
    }

    private DevicesEntity saveNewDevice(AppStartRequest requestBody, UsersEntity user) {
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

    private AppStartResponse createResponseForRegularAppStart(AppStartRequest requestBody, UsersEntity user) {
        AppStartResponse responseBody = initResponseWithFreshExceptions(requestBody, user);
        addFreshExceptionTypesToResponse(requestBody, responseBody);
        addCommonDataToResponse(user, responseBody);
        return responseBody;
    }

    private AppStartResponse initResponseWithFreshExceptions(AppStartRequest requestBody, UsersEntity user) {
        List<ExceptionInstancesEntity> exceptions = exceptionInstanceCrud
                .findLastExceptionsNotAmongIds(user, requestBody.getKnownExceptionIds());
        AppStartResponse response = new AppStartResponse();
        response.setMyExceptions(exceptions.stream().map(ExceptionInstanceWrapper::new).collect(Collectors.toList()));
        return response;
    }

    private void addFreshExceptionTypesToResponse(AppStartRequest requestBody, AppStartResponse responseBody) {
        int knownVersion = requestBody.getExceptionVersion();
        int currentVersion = constantCrud.getExceptionVersion();
        if (currentVersion > knownVersion) {
            List<ExceptionTypesEntity> exceptions = exceptionTypeCrud.findNewerTypesThanVersion(knownVersion);
            responseBody.setExceptionTypes(exceptions.stream().map(ExceptionTypeWrapper::new).collect(Collectors.toList()));
        }
        responseBody.setExceptionVersion(currentVersion);
    }
}
