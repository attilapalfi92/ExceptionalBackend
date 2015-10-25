package com.attilapalf.exceptional.services

import com.attilapalf.exceptional.entities.Device
import com.attilapalf.exceptional.entities.User
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
    private lateinit var userCrud: UserCrud
    @Autowired
    private lateinit var deviceCrud: DeviceCrud
    @Autowired
    private lateinit var exceptionInstanceCrud: ExceptionInstanceCrud
    @Autowired
    private lateinit var constantCrud: ConstantCrud
    @Autowired
    private lateinit var friendshipCrud: FriendshipCrud
    @Autowired
    private lateinit var exceptionTypeCrud: ExceptionTypeCrud
    @Autowired
    private lateinit var beingVotedCrud: BeingVotedCrud
    @Autowired
    private lateinit var gcmMessageService: GcmMessageService

    @Transactional
    override public fun firstAppStart(requestBody: AppStartRequest): AppStartResponse {
        var user: User? = userCrud.findOne(requestBody.userFacebookId)
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

    private fun saveNewUser(requestBody: AppStartRequest): User {
        val user = User()
        user.facebookId = requestBody.userFacebookId
        user.points = constantCrud.startingPoint
        return updateUserName(requestBody, user)
    }

    private fun updateUserName(requestBody: AppStartRequest, user: User): User {
        user.firstName = requestBody.firstName
        user.lastName = requestBody.lastName
        return userCrud.save(user)
    }

    private fun handleUserFirstStart(requestBody: AppStartRequest, user: User): AppStartResponse {
        saveDeviceForUser(requestBody, user)
        removeInvalidFriendships(requestBody, user)
        val friends = saveNewFriends(requestBody, user)
        gcmMessageService.sendFriendNotification(user, friends)
        return createResponseForFirstAppStart(user)
    }

    private fun removeInvalidFriendships(requestBody: AppStartRequest, user: User) {
        val existingFriends = friendshipCrud.findUsersExistingFriends(user)
        val existingFriendIds = existingFriends.map { it.facebookId } as MutableList
        existingFriendIds.removeAll(requestBody.friendsFacebookIds)
        friendshipCrud.deleteFriendships(user, existingFriendIds)
    }

    private fun saveNewFriends(requestBody: AppStartRequest, user: User): List<User> {
        val currentValidFriendIds = requestBody.friendsFacebookIds
        userCrud.saveUsersIfNew(currentValidFriendIds)
        return saveNewFriendships(user, currentValidFriendIds)
    }

    private fun createResponseForFirstAppStart(user: User): AppStartResponse {
        val responseBody = initResponseWithExceptions(user)
        addExceptionTypesToResponse(responseBody)
        addCommonDataToResponse(user, responseBody)
        return responseBody
    }

    private fun saveNewFriendships(user: User, currentValidFriendIds: MutableList<BigInteger>): List<User> {
        val existingFriends = friendshipCrud.findUsersExistingFriends(user)
        val existingFriendIds = existingFriends.map { it.facebookId }
        currentValidFriendIds.removeAll(existingFriendIds)
        friendshipCrud.saveFriendships(user, userCrud.userIdsToUsersEntities(currentValidFriendIds))
        return existingFriends
    }

    private fun initResponseWithExceptions(user: User): AppStartResponse {
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

    private fun addCommonDataToResponse(user: User, responseBody: AppStartResponse) {
        addVotedExceptionsToResponse(responseBody)
        addVoteMetadataToResponse(user, responseBody)
        addFriendsPointsToResponse(responseBody, user)
        responseBody.points = user.points
    }

    private fun addFriendsPointsToResponse(responseBody: AppStartResponse, user: User) {
        val friends = friendshipCrud.findUsersExistingFriends(user)
        responseBody.friendsPoints = friends.toMap({ it.facebookId }, { it.points })
    }

    private fun addVoteMetadataToResponse(user: User, responseBody: AppStartResponse) {
        responseBody.votedThisWeek = user.votedForException != null
        responseBody.submittedThisWeek = user.mySubmissionForVote != null
    }

    private fun addVotedExceptionsToResponse(responseBody: AppStartResponse) {
        val typeWrapperList = ArrayList<ExceptionTypeWrapper>()
        beingVotedCrud.findAll().forEach { exception -> typeWrapperList.add(ExceptionTypeWrapper(exception)) }
        responseBody.beingVotedTypes = typeWrapperList
    }

    private fun saveDeviceForUser(requestBody: AppStartRequest, user: User) {
        var device: Device? = deviceCrud.findOne(requestBody.deviceId)
        if (device == null) {
            device = saveNewDevice(requestBody, user)
        }
        saveUserWithDevice(user, device)
    }

    private fun saveNewDevice(requestBody: AppStartRequest, user: User): Device {
        val device = Device()
        device.deviceId = requestBody.deviceId
        device.user = user
        device.gcmId = requestBody.gcmId
        device.deviceName = requestBody.deviceName
        return deviceCrud.save(device)
    }

    private fun saveUserWithDevice(user: User, device: Device) {
        if (user.devices == null) {
            user.devices = ArrayList<Device>()
        }
        user.devices.add(device)
        userCrud.save(user)
    }

    private fun createResponseForRegularAppStart(requestBody: AppStartRequest, user: User): AppStartResponse {
        val responseBody = initResponseWithFreshExceptions(requestBody, user)
        addFreshExceptionTypesToResponse(requestBody, responseBody)
        addCommonDataToResponse(user, responseBody)
        return responseBody
    }

    private fun initResponseWithFreshExceptions(requestBody: AppStartRequest, user: User): AppStartResponse {
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
