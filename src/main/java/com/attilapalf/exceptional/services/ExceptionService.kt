package com.attilapalf.exceptional.services

import com.attilapalf.exceptional.entities.ExceptionInstance
import com.attilapalf.exceptional.entities.User
import com.attilapalf.exceptional.messages.*
import com.attilapalf.exceptional.repositories.exceptioninstances_.ExceptionInstanceCrud
import com.attilapalf.exceptional.repositories.users.UserCrud
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
        val exception = updateDatabase(instanceWrapper, users)
        instanceWrapper.instanceId = exception.id
        gcmMessageService.sendExceptionNotification(users.sender, users.receiver, exception, instanceWrapper.question)
        return ExceptionSentResponse(exception.type.shortName, users.sender.points, users.receiver.points, instanceWrapper)
    }

    private fun findUsers(instanceWrapper: ExceptionInstanceWrapper) =
            Users(
                    sender = userCrud.findOne(instanceWrapper.fromWho),
                    receiver = userCrud.findOne(instanceWrapper.toWho)
            )

    private fun updateDatabase(instanceWrapper: ExceptionInstanceWrapper, users: Users): ExceptionInstance {
        calculatePoints(instanceWrapper)
        updateUsersPoints(users.sender, users.receiver, instanceWrapper)
        val exception = exceptionCrud.saveNewException(instanceWrapper)
        return exception
    }

    private fun calculatePoints(instanceWrapper: ExceptionInstanceWrapper) {
        if ( !instanceWrapper.question.hasQuestion ) {
            instanceWrapper.pointsForSender = 25
            instanceWrapper.pointsForReceiver = -20
        } else {
            instanceWrapper.pointsForSender = 0
            instanceWrapper.pointsForReceiver = 0
        }
    }

    private fun updateUsersPoints(sender: User, receiver: User, instanceWrapper: ExceptionInstanceWrapper) {
        sender.points = sender.points + instanceWrapper.pointsForSender
        receiver.points = receiver.points + instanceWrapper.pointsForReceiver
        userCrud.save(sender)
        userCrud.save(receiver)
    }

    @Transactional
    override fun refreshExceptions(requestBody: BaseExceptionRequest): ExceptionRefreshResponse {
        val user = userCrud.findOne(requestBody.userFacebookId)
        val exceptions = exceptionCrud.findLastExceptionsNotAmongIds(
                user,
                requestBody.knownExceptionIds)
        return ExceptionRefreshResponse(exceptions.map { ExceptionInstanceWrapper(it) })
    }

    @Transactional
    override fun answerQuestion(questionAnswer: QuestionAnswer): ExceptionSentResponse {
        val exception = exceptionCrud.findOne(questionAnswer.exceptionInstanceId)
        updatePoints(exception, questionAnswer)
        exception.isAnswered = true
        exceptionCrud.save(exception)
        return ExceptionSentResponse(exception.type.shortName, exception.fromUser.points, exception.toUser.points,
                ExceptionInstanceWrapper(exception))
    }

    private fun updatePoints(exception: ExceptionInstance, questionAnswer: QuestionAnswer) {
        if ( exception.isYesIsCorrect != questionAnswer.answeredYes ) {
            exception.pointsForSender = 50;
            exception.pointsForReceiver = -40;
            exception.fromUser.points += exception.pointsForSender;
            exception.toUser.points += exception.pointsForReceiver;
        }
    }

    private data class Users(val sender: User, val receiver: User)
}