package com.attilapalf.exceptional.messages.notifications

import com.attilapalf.exceptional.entities.ExceptionInstance
import java.math.BigInteger

/**
 * Created by palfi on 2015-10-17.
 */
public class AnswerNotification(toGcmIds: List<String>, exception: ExceptionInstance) : BaseNotification(toGcmIds) {
    public val data: Data

    init {
        data = Data(exception.pointsForSender, exception.pointsForReceiver, exception.id)
    }

    public class Data(val usersPoints: Int?,
                      val friendsPoints: Int?,
                      val instanceId: BigInteger?) : BaseMessageData("answer")
}