package com.attilapalf.exceptional.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.Protocol
import java.net.URI

/**
 * Created by palfi on 2015-10-22.
 */
@Configuration
public open class RedisConfig {
    @Autowired
    private lateinit val redisProperties: RedisConfigProperties

    @Bean
    public open fun jedisPool(): JedisPool {
        val uri = URI(redisProperties.url)
        return JedisPool(
                JedisPoolConfig(), uri.host, uri.port, Protocol.DEFAULT_TIMEOUT,
                uri.userInfo.split(delimiters = ":", ignoreCase = true, limit = 2)[1]
        )
    }
}
