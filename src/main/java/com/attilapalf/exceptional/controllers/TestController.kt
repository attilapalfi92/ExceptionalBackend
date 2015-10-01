package com.attilapalf.exceptional.controllers

import com.attilapalf.exceptional.services.VotingService
import org.springframework.beans.factory.annotation.Autowired
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

    @RequestMapping("/test/resetVoting")
    public fun resetVoting(): ResponseEntity<String> {
        votingService.resetVoting()
        return ResponseEntity.ok( "Reset successfully." );
    }

}