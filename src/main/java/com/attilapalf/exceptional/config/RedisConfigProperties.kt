package com.attilapalf.exceptional.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Created by palfi on 2015-10-22.
 */
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfigProperties {
    public var url: String = ""
}