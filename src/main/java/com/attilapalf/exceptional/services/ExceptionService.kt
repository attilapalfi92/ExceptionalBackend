package com.attilapalf.exceptional.services

import com.attilapalf.exceptional.entities.ExceptionInstancesEntity
import com.attilapalf.exceptional.entities.UsersEntity
import com.attilapalf.exceptional.messages.*
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
    fun answerQuestion(questionAnswer: QuestionAnswer): ExceptionSentResponse
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
    override fun throwException(instanceWrapper: ExceptionInstanceWrapper): ExceptionSentResponse {
        val users = findUsers(instanceWrapper)
        updateUsersPoints(users.first, users.second, instanceWrapper.question)
        val exception = exceptionCrud.saveNewException(instanceWrapper)
        instanceWrapper.instanceId = exception.id
        gcmMessageService.sendExceptionNotification(users.first, users.second, exception, instanceWrapper.question)
        return createExceptionSentResponse(users.first, users.second, instanceWrapper, exception)
    }

    private fun findUsers(instanceWrapper: ExceptionInstanceWrapper): Pair<UsersEntity, UsersEntity> {
        return Pair(userCrud.findOne(instanceWrapper.fromWho),
                userCrud.findOne(instanceWrapper.toWho))
    }

    private fun updateUsersPoints(sender: UsersEntity, receiver: UsersEntity, question: Question) {
        if ( !question.hasQuestion ) {
            sender.points = sender.points + 25
            receiver.points = receiver.points - 20
            userCrud.save(sender)
            userCrud.save(receiver)
        }
    }

    private fun createExceptionSentResponse(sender: UsersEntity, receiver: UsersEntity,
                                            instanceWrapper: ExceptionInstanceWrapper,
                                            exception: ExceptionInstancesEntity): ExceptionSentResponse {
        return ExceptionSentResponse(
                exception.type.shortName,
                sender.points,
                receiver.points,
                instanceWrapper)
    }

    @Transactional
    override fun refreshExceptions(requestBody: BaseExceptionRequest): ExceptionRefreshResponse {
        val user = userCrud.findOne(requestBody.userFacebookId)
        val exceptions = exceptionCrud.findLastExceptionsNotAmongIds(
                user,
                requestBody.knownExceptionIds)
        return ExceptionRefreshResponse(exceptions.map { ExceptionInstanceWrapper(it) }, listOf())
    }

    @Transactional
    override fun answerQuestion(questionAnswer: QuestionAnswer): ExceptionSentResponse {
        val exception = exceptionCrud.findOne(questionAnswer.exceptionInstanceId)
        updateUsersPoints(exception, questionAnswer)
        exception.isAnswered = true
        exceptionCrud.save(exception)
        return ExceptionSentResponse(
                exception.type.shortName,
                exception.fromUser.points,
                exception.toUser.points,
                ExceptionInstanceWrapper(exception))
    }

    private fun updateUsersPoints(exception: ExceptionInstancesEntity, questionAnswer: QuestionAnswer) {
        if ( exception.isYesIsCorrect != questionAnswer.answeredYes ) {
            exception.fromUser.points = exception.fromUser.points + 50
            exception.toUser.points = exception.toUser.points - 40
        }
    }
}