package com.attilapalf.exceptional.controllers

import com.attilapalf.exceptional.services.GlobalStatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by palfi on 2015-10-21.
 */
@RestController
public class StatController {
    @Autowired
    private lateinit var globalStatService: GlobalStatService

    @RequestMapping("stats/globalPoint")
    public fun getGlobalPointsStat(): ResponseEntity<String> {
        return ResponseEntity(globalStatService.globalPointsStat(), HttpStatus.OK)
    }

    @RequestMapping("stats/globalThrowCounts")
    public fun getGlobalThrowCounts(): ResponseEntity<String> {
        return ResponseEntity(globalStatService.globalThrowCounts(), HttpStatus.OK)
    }
}