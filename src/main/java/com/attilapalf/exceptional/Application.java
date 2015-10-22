package com.attilapalf.exceptional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import redis.clients.jedis.Jedis;

/**
 * Created by Attila on 2015-06-10.
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main( String[] args ) {
        SpringApplication.run( Application.class, args );
    }
}
