package com.attilapalf.exceptional.services

import com.attilapalf.exceptional.entities.DevicesEntity
import com.attilapalf.exceptional.entities.UsersEntity
import com.attilapalf.exceptional.messages.AppStartRequest
import com.attilapalf.exceptional.messages.AppStartResponse
import com.attilapalf.exceptional.messages.ExceptionInstanceWrapper
import com.attilapalf.exceptional.messages.ExceptionTypeWrapper
import com.attilapalf.exceptional.repositories.being_voted_exception_types.BeingVotedCrud
import com.attilapalf.exceptional.repositories.constants.ConstantCrud
import com.attilapalf.exceptional.repositories.devices.DeviceCrud
import com.attilapalf.exceptional.repositories.exceptioninstances_.ExceptionInstanceCrud
import com.attilapalf.exceptional.repositories.exceptiontypes.ExceptionTypeCrud
import com.attilapalf.exceptional.repositories.friendships.FriendshipCrud
import com.attilapalf.exceptional.repositories.users.UserCrud
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger
import java.util.*

/**
 * Created by Attila on 2015-06-11.
 */
public interface AppStartService {
    fun firstAppStart(requestBody: AppStartRequest): AppStartResponse
    fun regularAppStart(requestBody: AppStartRequest): AppStartResponse
}

@Service
public class AppStartServiceImpl : AppStartService {
    @Autowired
    private lateinit val userCrud: UserCrud
    @Autowired
    private lateinit val deviceCrud: DeviceCrud
    @Autowired
    private lateinit val exceptionInstanceCrud: ExceptionInstanceCrud
    @Autowired
    private lateinit val constantCrud: ConstantCrud
    @Autowired
    private lateinit val friendshipCrud: FriendshipCrud
    @Autowired
    private lateinit val exceptionTypeCrud: ExceptionTypeCrud
    @Autowired
    private lateinit val beingVotedCrud: BeingVotedCrud
    @Autowired
    private lateinit val gcmMessageService: GcmMessageService

    @Transactional
    override public fun firstAppStart(requestBody: AppStartRequest): AppStartResponse {
        var user: UsersEntity? = userCrud.findOne(requestBody.userFacebookId)
        if (user == null) {
            user = saveNewUser(requestBody)
        }
        updateUserName(requestBody, user)
        return handleUserFirstStart(requestBody, user)
    }

    @Transactional
    override public fun regularAppStart(requestBody: AppStartRequest): AppStartResponse {
        val user = userCrud.findOne(requestBody.userFacebookId)
        updateUserName(requestBody, user)
        removeInvalidFriendships(requestBody, user)
        saveNewFriends(requestBody, user)
        return createResponseForRegularAppStart(requestBody, user)
    }

    private fun saveNewUser(requestBody: AppStartRequest): UsersEntity {
        val user = UsersEntity()
        user.facebookId = requestBody.userFacebookId
        user.points = constantCrud.startingPoint
        return updateUserName(requestBody, user)
    }

    private fun updateUserName(requestBody: AppStartRequest, user: UsersEntity): UsersEntity {
        user.firstName = requestBody.firstName
        user.lastName = requestBody.lastName
        return userCrud.save(user)
    }

    private fun handleUserFirstStart(requestBody: AppStartRequest, user: UsersEntity): AppStartResponse {
        saveDeviceForUser(requestBody, user)
        removeInvalidFriendships(requestBody, user)
        val friends = saveNewFriends(requestBody, user)
        gcmMessageService.sendFriendNotification(user, friends)
        return createResponseForFirstAppStart(user)
    }

    private fun removeInvalidFriendships(requestBody: AppStartRequest, user: UsersEntity) {
        val existingFriends = friendshipCrud.findUsersExistingFriends(user)
        val existingFriendIds = existingFriends.map { it.facebookId } as MutableList
        existingFriendIds.removeAll(requestBody.friendsFacebookIds)
        friendshipCrud.deleteFriendships(user, existingFriendIds)
    }

    private fun saveNewFriends(requestBody: AppStartRequest, user: UsersEntity): List<UsersEntity> {
        val currentValidFriendIds = requestBody.friendsFacebookIds
        userCrud.saveUsersIfNew(currentValidFriendIds)
        return saveNewFriendships(user, currentValidFriendIds)
    }

    private fun createResponseForFirstAppStart(user: UsersEntity): AppStartResponse {
        val responseBody = initResponseWithExceptions(user)
        addExceptionTypesToResponse(responseBody)
        addCommonDataToResponse(user, responseBody)
        return responseBody
    }

    private fun saveNewFriendships(user: UsersEntity, currentValidFriendIds: MutableList<BigInteger>): List<UsersEntity> {
        val existingFriends = friendshipCrud.findUsersExistingFriends(user)
        val existingFriendIds = existingFriends.map { it.facebookId }
        currentValidFriendIds.removeAll(existingFriendIds)
        friendshipCrud.saveFriendships(user, userCrud.userIdsToUsersEntities(currentValidFriendIds))
        return existingFriends
    }

    private fun initResponseWithExceptions(user: UsersEntity): AppStartResponse {
        val exceptions = exceptionInstanceCrud.findLastExceptionsForUser(user)
        val responseBody = AppStartResponse()
        responseBody.myExceptions = exceptions.map { ExceptionInstanceWrapper(it) }
        return responseBody
    }

    private fun addExceptionTypesToResponse(responseBody: AppStartResponse) {
        val typeWrapperList = ArrayList<ExceptionTypeWrapper>()
        exceptionTypeCrud.findAll().forEach { exceptionType -> typeWrapperList.add(ExceptionTypeWrapper(exceptionType)) }
        responseBody.exceptionTypes = typeWrapperList
        responseBody.exceptionVersion = constantCrud.exceptionVersion
    }

    private fun addCommonDataToResponse(user: UsersEntity, responseBody: AppStartResponse) {
        addVotedExceptionsToResponse(responseBody)
        addVoteMetadataToResponse(user, responseBody)
        addFriendsPointsToResponse(responseBody, user)
        responseBody.points = user.points
        responseBody.exceptionQuestions = listOf()
    }

    private fun addFriendsPointsToResponse(responseBody: AppStartResponse, user: UsersEntity) {
        val friends = friendshipCrud.findUsersExistingFriends(user)
        responseBody.friendsPoints = friends.map { it.facebookId to it.points }.toMap()
    }

    private fun addVoteMetadataToResponse(user: UsersEntity, responseBody: AppStartResponse) {
        responseBody.votedThisWeek = user.votedForException != null
        responseBody.submittedThisWeek = user.mySubmissionForVote != null
    }

    private fun addVotedExceptionsToResponse(responseBody: AppStartResponse) {
        val typeWrapperList = ArrayList<ExceptionTypeWrapper>()
        beingVotedCrud.findAll().forEach { exception -> typeWrapperList.add(ExceptionTypeWrapper(exception)) }
        responseBody.beingVotedTypes = typeWrapperList
    }

    private fun saveDeviceForUser(requestBody: AppStartRequest, user: UsersEntity) {
        var device: DevicesEntity? = deviceCrud.findOne(requestBody.deviceId)
        if (device == null) {
            device = saveNewDevice(requestBody, user)
        }
        saveUserWithDevice(user, device)
    }

    private fun saveNewDevice(requestBody: AppStartRequest, user: UsersEntity): DevicesEntity {
        val device = DevicesEntity()
        device.deviceId = requestBody.deviceId
        device.user = user
        device.gcmId = requestBody.gcmId
        device.deviceName = requestBody.deviceName
        return deviceCrud.save(device)
    }

    private fun saveUserWithDevice(user: UsersEntity, device: DevicesEntity) {
        if (user.devices == null) {
            user.devices = ArrayList<DevicesEntity>()
        }
        user.devices.add(device)
        userCrud.save(user)
    }

    private fun createResponseForRegularAppStart(requestBody: AppStartRequest, user: UsersEntity): AppStartResponse {
        val responseBody = initResponseWithFreshExceptions(requestBody, user)
        addFreshExceptionTypesToResponse(requestBody, responseBody)
        addCommonDataToResponse(user, responseBody)
        return responseBody
    }

    private fun initResponseWithFreshExceptions(requestBody: AppStartRequest, user: UsersEntity): AppStartResponse {
        val exceptions = exceptionInstanceCrud.findLastExceptionsNotAmongIds(user, requestBody.knownExceptionIds)
        val response = AppStartResponse()
        response.myExceptions = exceptions.map { ExceptionInstanceWrapper(it) }
        return response
    }

    private fun addFreshExceptionTypesToResponse(requestBody: AppStartRequest, responseBody: AppStartResponse) {
        val knownVersion = requestBody.exceptionVersion
        val currentVersion = constantCrud.exceptionVersion
        if (currentVersion > knownVersion) {
            val exceptions = exceptionTypeCrud.findNewerTypesThanVersion(knownVersion)
            responseBody.exceptionTypes = exceptions.map { ExceptionTypeWrapper(it) }
        }
        responseBody.exceptionVersion = currentVersion
    }
}
