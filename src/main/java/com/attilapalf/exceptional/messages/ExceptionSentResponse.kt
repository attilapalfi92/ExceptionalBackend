package com.attilapalf.exceptional.messages

/**
 * Created by Attila on 2015-06-21.
 */
public data class ExceptionSentResponse(var exceptionShortName: String,
                                        var sendersPoints: Int,
                                        var receiversPoints: Int,
                                        var instanceWrapper: ExceptionInstanceWrapper);
