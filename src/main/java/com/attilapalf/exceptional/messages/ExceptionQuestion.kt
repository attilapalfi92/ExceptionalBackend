package com.attilapalfi.exceptional.model

import com.attilapalf.exceptional.messages.ExceptionInstanceWrapper
import java.math.BigInteger

/**
 * Created by palfi on 2015-10-03.
 */
public data class ExceptionQuestion(
        public var question: Question = Question(),
        public var fromWhoId: BigInteger = BigInteger("0"),
        public var exception: ExceptionInstanceWrapper = ExceptionInstanceWrapper()) : Comparable<ExceptionQuestion> {

    override fun compareTo(other: ExceptionQuestion) =
            other.exception.instanceId.compareTo(exception.instanceId)
}


