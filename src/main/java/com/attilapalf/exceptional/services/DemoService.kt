package com.attilapalf.exceptional.services

import com.attilapalf.exceptional.entities.User
import com.attilapalf.exceptional.repositories.users.UserCrud
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by palfi on 2015-11-26.
 */
public interface DemoService {
    fun printHighPointUsers()
    fun saveMeAndPrintCount()
    fun deleteTopUser()
}

@Service
public class DemoServiceImpl : DemoService {
    @Autowired
    private lateinit var userCrud: UserCrud

    override fun printHighPointUsers() {
        val highPointUsers = userCrud.findAll()
                .filter { it.points > 1000 }
                .map { user -> Pair(user.firstName, user.points) }

        println(highPointUsers)
    }

    override fun saveMeAndPrintCount() {
        val userToSave = User()
        userToSave.firstName = "Attila";
        userToSave.lastName = "Pálfi";
        userToSave.points = 5000;
        userCrud.save(userToSave)
        println("Number of all users: ${userCrud.count()}")
    }

    override fun deleteTopUser() {
        val topUser = userCrud.findAll().maxBy { it.points }
        userCrud.delete(topUser)
    }
}