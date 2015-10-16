package com.attilapalf.exceptional.controllers

import com.attilapalf.exceptional.entities.ExceptionInstance
import com.attilapalf.exceptional.messages.notifications.AnswerNotification
import com.attilapalf.exceptional.repositories.exceptioninstances_.ExceptionInstanceCrud
import com.attilapalf.exceptional.services.VotingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by palfi on 2015-09-30.
 */
@Controller
public class TestController {
    @Autowired
    private lateinit val votingService: VotingService

    @Autowired
    private lateinit val exceptionCrud: ExceptionInstanceCrud

    @RequestMapping("/test/resetVoting")
    fun resetVoting(): ResponseEntity<String> {
        votingService.resetVoting()
        return ResponseEntity.ok("Reset successfully.");
    }

    @RequestMapping("/test/answered")
    fun testAnswered(): ResponseEntity<String> {
        val exception = exceptionCrud.findAll().first()
        exception.isAnswered = !exception.isAnswered
        exceptionCrud.save(exception)
        return ResponseEntity("Exception answered set to ${exception.isAnswered}.", HttpStatus.OK)
    }

    @RequestMapping("/test/answerNotification")
    fun testAnswerNotification(): ResponseEntity<AnswerNotification> {
        return ResponseEntity(AnswerNotification(listOf("hurka_gcm_id"), ExceptionInstance()), HttpStatus.OK);
    }
}