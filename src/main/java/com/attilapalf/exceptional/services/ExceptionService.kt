package com.attilapalf.exceptional.services

import com.attilapalf.exceptional.entities.ExceptionInstancesEntity
import com.attilapalf.exceptional.entities.UsersEntity
import com.attilapalf.exceptional.messages.BaseExceptionRequest
import com.attilapalf.exceptional.messages.ExceptionInstanceWrapper
import com.attilapalf.exceptional.messages.ExceptionRefreshResponse
import com.attilapalf.exceptional.messages.ExceptionSentResponse
import com.attilapalf.exceptional.repositories.exceptioninstances_.ExceptionInstanceCrud
import com.attilapalf.exceptional.repositories.users.UserCrud
import com.attilapalfi.exceptional.model.Question
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by palfi on 2015-10-02.
 */
public interface ExceptionService {
    fun throwException(instanceWrapper: ExceptionInstanceWrapper): ExceptionSentResponse
    fun refreshExceptions(requestBody: BaseExceptionRequest): ExceptionRefreshResponse
}

@Service
public class ExceptionServiceImpl : ExceptionService {
    @Autowired
    private lateinit val exceptionCrud: ExceptionInstanceCrud
    @Autowired
    private lateinit val userCrud: UserCrud
    @Autowired
    private lateinit val gcmMessageService: GcmMessageService


    @Transactional
    override public fun throwException(instanceWrapper: ExceptionInstanceWrapper): ExceptionSentResponse {
        val users = findUsers(instanceWrapper)
        updateUsersPoints(users, instanceWrapper.question)
        val exception = exceptionCrud.saveNewException(instanceWrapper)
        instanceWrapper.instanceId = exception.id
        gcmMessageService.sendExceptionNotification(users.first, users.second, exception, instanceWrapper.question)
        return createExceptionSentResponse(users, instanceWrapper, exception)
    }

    private fun findUsers(instanceWrapper: ExceptionInstanceWrapper): Pair<UsersEntity, UsersEntity> {
        return Pair(userCrud.findOne(instanceWrapper.fromWho),
                userCrud.findOne(instanceWrapper.toWho))
    }

    private fun updateUsersPoints(users: Pair<UsersEntity, UsersEntity>, question: Question) {
        if ( !question.hasQuestion ) {
            users.first.points = users.first.points + 25
            users.second.points = users.second.points - 20
            userCrud.save(users.first)
            userCrud.save(users.second)
        }
    }

    private fun createExceptionSentResponse(users: Pair<UsersEntity, UsersEntity>,
                                            instanceWrapper: ExceptionInstanceWrapper,
                                            exception: ExceptionInstancesEntity): ExceptionSentResponse {
        return ExceptionSentResponse(
                instanceWrapper,
                exception.type.shortName,
                users.first.points,
                users.second.points
        )
    }

    @Transactional
    override public fun refreshExceptions(requestBody: BaseExceptionRequest): ExceptionRefreshResponse {
        val user = userCrud.findOne(requestBody.userFacebookId)
        val exceptions = exceptionCrud.findLastExceptionsNotAmongIds(
                user,
                requestBody.knownExceptionIds)
        return ExceptionRefreshResponse(exceptions.map { ExceptionInstanceWrapper(it) }, listOf())
    }
}