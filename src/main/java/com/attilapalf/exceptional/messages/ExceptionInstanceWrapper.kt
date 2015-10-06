package com.attilapalf.exceptional.messages

import com.attilapalf.exceptional.entities.ExceptionInstancesEntity
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
                                           var question: Question = Question()) {
    constructor(e: ExceptionInstancesEntity) : this(
            e.fromUser.facebookId,
            e.toUser.facebookId,
            e.dateTime.time,
            e.longitude,
            e.latitude,
            e.type.id,
            e.id,
            Question(e.questionText, e.isYesIsCorrect, e.isHasQuestion, e.isAnswered)
    )
};