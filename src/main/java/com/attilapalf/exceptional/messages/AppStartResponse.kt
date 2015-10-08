package com.attilapalf.exceptional.messages

import java.math.BigInteger

/**
 * Created by palfi on 2015-10-03.
 */
public data class AppStartResponse(var myExceptions: List<ExceptionInstanceWrapper> = listOf(),
                                   var exceptionTypes: List<ExceptionTypeWrapper> = listOf(),
                                   var beingVotedTypes: List<ExceptionTypeWrapper> = listOf(),
                                   var friendsPoints: Map<BigInteger, Int> = mapOf(),
                                   var points: Int = 100,
                                   var exceptionVersion: Int = 0,
                                   var submittedThisWeek: Boolean = true,
                                   var votedThisWeek: Boolean = true);