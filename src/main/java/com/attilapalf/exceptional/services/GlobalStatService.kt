package com.attilapalf.exceptional.services

import com.attilapalf.exceptional.entities.User
import com.attilapalf.exceptional.repositories.exceptioninstances_.ExceptionInstanceCrud
import com.attilapalf.exceptional.repositories.exceptiontypes.ExceptionTypeCrud
import com.attilapalf.exceptional.repositories.users.UserCrud
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import redis.clients.jedis.JedisPool
import java.math.BigInteger
import java.util.*
import javax.annotation.PostConstruct

/**
 * Created by palfi on 2015-10-21.
 */
public interface GlobalStatService {
    fun globalPointsStat(): String
    fun globalThrowCounts(): String
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
    @Autowired
    private lateinit val jedisPool: JedisPool
    private val objectMapper = ObjectMapper()
    private val POINT_STATS = "POINT_STATS"
    private val THROW_COUNT = "THROW_COUNT"

    @PostConstruct
    private fun init() {
        recalculatePointStat()
        recalculateThrowCounts()
    }


    override fun globalPointsStat(): String {
        jedisPool.resource.use {
            return it.get(POINT_STATS)
        }
    }

    override fun globalThrowCounts(): String {
        jedisPool.resource.use {
            return it.get(THROW_COUNT)
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 5)
    private fun recalculatePointStat() {
        val globalPointStatistics = LinkedHashMap<BigInteger, Int>()
        val allUsers = userCrud.findAllOrderedByPoints()
        putUsers(allUsers, globalPointStatistics)
        putToJedis(POINT_STATS, objectMapper.writeValueAsString(globalPointStatistics))
    }

    private fun putUsers(allUsers: MutableList<User>, globalPointStatistics: LinkedHashMap<BigInteger, Int>) {
        if ( allUsers.count() <= DATAPOINT_NUMBER ) {
            allUsers.forEach { globalPointStatistics.put(it.facebookId, it.points) }
        } else {
            putSelectedUsers(allUsers, globalPointStatistics)
        }
    }

    private fun putSelectedUsers(allUsers: MutableList<User>, globalPointStatistics: LinkedHashMap<BigInteger, Int>) {
        val lowestUser = allUsers.get(0)
        val highestUser = allUsers.get(allUsers.lastIndex)
        globalPointStatistics.put(lowestUser.facebookId, lowestUser.points)
        putMiddleOnes(allUsers, globalPointStatistics)
        globalPointStatistics.put(highestUser.facebookId, highestUser.points)
    }

    private fun putMiddleOnes(allUsers: MutableList<User>, globalPointStatistics: LinkedHashMap<BigInteger, Int>) {
        val quotient = allUsers.count() / (DATAPOINT_NUMBER - 2)
        for (i in 1..allUsers.lastIndex - 1) {
            if ( ( i + (quotient / 2) ) % quotient == 0 ) {
                globalPointStatistics.put(allUsers[i].facebookId, allUsers[i].points)
            }
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 5)
    private fun recalculateThrowCounts() {
        val globalThrowCounts = LinkedHashMap<Int, Int>()
        val instances = exceptionInstanceCrud.findAll()
        exceptionTypeCrud.findAll().forEach {
            type ->
            globalThrowCounts.put(type.id, instances.count { it.type.equals(type) })
        }
        putToJedis(THROW_COUNT, objectMapper.writeValueAsString(globalThrowCounts))
    }

    private fun putToJedis(key: String, value: String) {
        jedisPool.resource.use {
            it.set(key, value)
        }
    }
}