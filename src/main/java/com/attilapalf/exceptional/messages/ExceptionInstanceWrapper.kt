package com.attilapalf.exceptional.messages

import com.attilapalf.exceptional.entities.ExceptionInstance
import com.attilapalfi.exceptional.model.Question
import java.math.BigInteger

/**
 * Created by palfi on 2015-10-02.
 */
public data class ExceptionInstanceWrapper(var fromWho: BigInteger = BigInteger("0"),
                                           var toWho: BigInteger = BigInteger("0"),
                                           var timeInMillis: Long = 0,
                                           var longitude: Double = 0.0,
                                           var latitude: Double = 0.0,
                                           var exceptionTypeId: Int = 0,
                                           var instanceId: BigInteger = BigInteger("0"),
                                           var pointsForSender: Int = 25,
                                           var pointsForReceiver: Int = -20,
                                           var city: String = "unknown",
                                           var question: Question = Question()) {
    constructor(e: ExceptionInstance) : this(
            fromWho = e.fromUser.facebookId,
            toWho = e.toUser.facebookId,
            timeInMillis = e.dateTime.time,
            longitude = e.longitude,
            latitude = e.latitude,
            exceptionTypeId = e.type.id,
            instanceId = e.id,
            pointsForSender = e.pointsForSender,
            pointsForReceiver = e.pointsForReceiver,
            city = e.city,
            question = Question(
                    text = e.questionText,
                    yesIsCorrect = e.isYesIsCorrect,
                    hasQuestion = e.isHasQuestion,
                    answered = e.isAnswered,
                    answeredCorrectly = e.isAnsweredCorrectly
            )
    )
};