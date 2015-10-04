package com.attilapalf.exceptional.messages

import com.attilapalfi.exceptional.model.QuestionException

/**
 * Created by 212461305 on 2015.07.05..
 */
public data class ExceptionRefreshResponse(var exceptionList: List<ExceptionInstanceWrapper>,
                                           var questionExceptions: List<QuestionException>);