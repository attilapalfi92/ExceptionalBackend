package com.attilapalf.exceptional.controllers;

import com.attilapalf.exceptional.messages.SubmitRequest;
import com.attilapalf.exceptional.messages.SubmitResponse;
import com.attilapalf.exceptional.services.VotingService;
import com.attilapalf.exceptional.messages.VoteRequest;
import com.attilapalf.exceptional.messages.VoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by palfi on 2015-09-06.
 */
@RestController
public class VotingController {

    @Autowired
    private VotingService votingService;

    @RequestMapping(value = "/voting/vote", method = RequestMethod.POST)
    public ResponseEntity<VoteResponse> vote(@RequestBody VoteRequest voteRequest) {
        return new ResponseEntity<>(votingService.voteForType(voteRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "/voting/submit", method = RequestMethod.POST)
    public ResponseEntity<SubmitResponse> submit(@RequestBody SubmitRequest submitRequest) {
        throw new UnsupportedOperationException();
    }
}
