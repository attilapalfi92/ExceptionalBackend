package com.attilapalf.exceptional.services

import com.attilapalf.exceptional.entities.ExceptionInstance
import com.attilapalf.exceptional.entities.User
import com.attilapalf.exceptional.messages.notifications.AnswerNotification
import com.attilapalf.exceptional.messages.notifications.ExceptionNotification
import com.attilapalf.exceptional.messages.notifications.FriendNotification
import com.attilapalfi.exceptional.model.Question
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import javax.annotation.PostConstruct

/**
 * Created by palfi on 2015-08-29.
 */

public interface GcmMessageService {
    fun sendFriendNotification(newUser: User, friends: List<User>)
    fun sendExceptionNotification(sender: User, receiver: User,
                                  exception: ExceptionInstance, question: Question)

    fun sendAnswerNotification(exception: ExceptionInstance)
}

@Service
public class GcmMessageServiceImpl : GcmMessageService {
    private val URL = "https://android.googleapis.com/gcm/send"

    private val API_KEY = "AIzaSyCSwgwKHOuqBozM-JhhKYp6xnwFKs8xJrU"
    private val PROJECT_NUMBER = "947608408958"
    private lateinit var httpHeaders: HttpHeaders

    @PostConstruct
    public fun initHttpHeaders() {
        httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        httpHeaders.set("Authorization", "key=" + API_KEY)
    }

    override public fun sendFriendNotification(newUser: User, friends: List<User>) {
        val restTemplate = RestTemplate()
        friends.forEach { friend -> pushNewFriendNotification(friend, newUser, restTemplate) }
    }

    override public fun sendExceptionNotification(sender: User, receiver: User,
                                                  exception: ExceptionInstance, question: Question) {
        val receiverGcmIds = getGcmIds(receiver)
        val notification = ExceptionNotification(receiverGcmIds, exception, receiver.points, sender.points, question)
        val gcmRequestData = HttpEntity(notification, httpHeaders)
        try {
            val gcmResponse = RestTemplate().postForObject(URL, gcmRequestData, String::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun sendAnswerNotification(exception: ExceptionInstance) {
        val notification = AnswerNotification(getGcmIds(exception.fromUser), exception)
        val gcmRequestData = HttpEntity(notification, httpHeaders)
        try {
            val gcmResponse = RestTemplate().postForObject(URL, gcmRequestData, String::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun pushNewFriendNotification(receiver: User, newUser: User, restTemplate: RestTemplate) {
        val receiverGcmIds = getGcmIds(receiver)
        val notification = FriendNotification(
                newUser.facebookId,
                newUser.firstName.trim() + " " + newUser.lastName.trim(),
                receiverGcmIds)
        val gcmRequestData = HttpEntity(notification, httpHeaders)
        try {
            val gcmResponse = restTemplate.postForObject(URL, gcmRequestData, String::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getGcmIds(receiver: User): List<String> {
        return receiver.devices.map { it.gcmId }
    }
}
