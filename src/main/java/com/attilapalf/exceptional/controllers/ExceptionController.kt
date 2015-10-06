package com.attilapalf.exceptional.controllers

import com.attilapalf.exceptional.messages.*
import com.attilapalf.exceptional.services.ExceptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Attila on 2015-06-13.
 */
@RestController
public class ExceptionController {

    @Autowired
    private lateinit val exceptionService: ExceptionService

    @RequestMapping(value = "/exception", method = arrayOf(RequestMethod.POST))
    fun throwException(@RequestBody wrapper: ExceptionInstanceWrapper): ResponseEntity<ExceptionSentResponse> {
        val question = wrapper.question
        if (question.text != null && question.text.length() > 100) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(exceptionService.throwException(wrapper), HttpStatus.OK)
    }

    @RequestMapping(value = "/exception/refresh", method = arrayOf(RequestMethod.POST))
    fun refreshExceptions(@RequestBody requestBody: BaseExceptionRequest): ResponseEntity<ExceptionRefreshResponse> {
        return ResponseEntity(exceptionService.refreshExceptions(requestBody), HttpStatus.OK)
    }

    @RequestMapping(value = "/exception/answer", method = arrayOf(RequestMethod.POST))
    fun answerQuestion(@RequestBody questionAnswer: QuestionAnswer): ResponseEntity<ExceptionSentResponse> {
        return ResponseEntity(exceptionService.answerQuestion(questionAnswer), HttpStatus.OK)
    }
}
