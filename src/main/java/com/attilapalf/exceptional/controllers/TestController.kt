package com.attilapalf.exceptional.controllers

import com.attilapalf.exceptional.entities.ExceptionInstance
import com.attilapalf.exceptional.entities.User
import com.attilapalf.exceptional.messages.notifications.AnswerNotification
import com.attilapalf.exceptional.repositories.devices.DeviceCrud
import com.attilapalf.exceptional.repositories.exceptioninstances_.ExceptionInstanceCrud
import com.attilapalf.exceptional.repositories.users.UserCrud
import com.attilapalf.exceptional.services.VotingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigInteger

/**
 * Created by palfi on 2015-09-30.
 */
@RestController
public class TestController {
    @Autowired
    private lateinit var votingService: VotingService
    @Autowired
    private lateinit var exceptionCrud: ExceptionInstanceCrud
    @Autowired
    private lateinit var userCrud: UserCrud
    @Autowired
    private lateinit var deviceCrud: DeviceCrud

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

    @RequestMapping("/test/insertUsers/{count}")
    fun insertRandomUsers(@PathVariable("count") count: Int): ResponseEntity<String> {
        for (i in 0..count - 1) {
            val user = createUser()
            userCrud.save(user)
        }
        return ResponseEntity("Created: $count. Total number of users: ${userCrud.findAll().count()}", HttpStatus.OK)
    }

    private fun createUser(): User {
        val user = User()
        user.facebookId = BigInteger.valueOf((Long.MAX_VALUE * Math.random()).toLong())
        user.points = (6000 * (Math.random() - 0.5)).toInt()
        user.firstName = ""; user.lastName = ""
        return user
    }
}