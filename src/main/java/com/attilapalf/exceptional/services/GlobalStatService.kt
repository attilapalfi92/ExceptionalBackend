package com.attilapalf.exceptional.services

import com.attilapalf.exceptional.entities.User
import com.attilapalf.exceptional.repositories.exceptioninstances_.ExceptionInstanceCrud
import com.attilapalf.exceptional.repositories.exceptiontypes.ExceptionTypeCrud
import com.attilapalf.exceptional.repositories.users.UserCrud
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.util.*
import javax.annotation.PostConstruct

/**
 * Created by palfi on 2015-10-21.
 */
public interface GlobalStatService {
    fun globalPointsStat(): LinkedHashMap<BigInteger, Int>
    fun globalThrowCounts(): LinkedHashMap<Int, Int>
}

private val DATAPOINT_NUMBER = 100

@Service
public class GlobalStatServiceImpl : GlobalStatService {
    @Autowired
    private lateinit val userCrud: UserCrud
    @Autowired
    private lateinit val exceptionInstanceCrud: ExceptionInstanceCrud
    @Autowired
    private lateinit val exceptionTypeCrud: ExceptionTypeCrud

    private var globalPointStatistics = LinkedHashMap<BigInteger, Int>()
    private val globPointStatSyncObject = Any()

    private var globalThrowCounts = LinkedHashMap<Int, Int>()
    private val globThrowCountSyncObject = Any()

    @PostConstruct
    private fun init() {
        recalculatePointStat()
        recalculateThrowCounts()
    }


    override fun globalPointsStat(): LinkedHashMap<BigInteger, Int> {
        synchronized(globPointStatSyncObject) {
            return globalPointStatistics
        }
    }

    override fun globalThrowCounts(): LinkedHashMap<Int, Int> {
        synchronized(globThrowCountSyncObject) {
            return globalThrowCounts
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 5)
    private fun recalculatePointStat() {
        synchronized(globPointStatSyncObject) {
            globalPointStatistics = LinkedHashMap()
            val allUsers = userCrud.findAllOrderedByPoints()
            putUsers(allUsers)
        }
    }

    private fun putUsers(allUsers: MutableList<User>) {
        if ( allUsers.count() <= DATAPOINT_NUMBER ) {
            allUsers.forEach { globalPointStatistics.put(it.facebookId, it.points) }
        } else {
            putSelectedUsers(allUsers)
        }
    }

    private fun putSelectedUsers(allUsers: MutableList<User>) {
        val lowestUser = allUsers.get(0)
        val highestUser = allUsers.get(allUsers.lastIndex)
        globalPointStatistics.put(lowestUser.facebookId, lowestUser.points)
        putMiddleOnes(allUsers)
        globalPointStatistics.put(highestUser.facebookId, highestUser.points)
    }

    private fun putMiddleOnes(allUsers: MutableList<User>) {
        val quotient = allUsers.count() / (DATAPOINT_NUMBER - 2)
        for (i in 1..allUsers.lastIndex - 1) {
            if ( ( i + (quotient / 2) ) % quotient == 0 ) {
                globalPointStatistics.put(allUsers[i].facebookId, allUsers[i].points)
            }
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 5)
    private fun recalculateThrowCounts() {
        synchronized(globThrowCountSyncObject) {
            globalThrowCounts = LinkedHashMap()
            val instances = exceptionInstanceCrud.findAll()
            exceptionTypeCrud.findAll().forEach {
                type ->
                globalThrowCounts.put(type.id, instances.count { it.type.equals(type) })
            }
        }
    }

}