package com.attilapalf.exceptional.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.attilapalf.exceptional.messages.*;
import com.attilapalf.exceptional.services.VotingService;

/**
 * Created by palfi on 2015-09-06.
 */
@RestController
public class VotingController {

    @Autowired
    private VotingService votingService;

    @RequestMapping( value = "/voting/vote", method = RequestMethod.POST )
    public ResponseEntity<VoteResponse> vote( @RequestBody VoteRequest voteRequest ) {
        return new ResponseEntity<>( votingService.voteForType( voteRequest ), HttpStatus.OK );
    }

    @RequestMapping( value = "/voting/submit", method = RequestMethod.POST )
    public ResponseEntity<SubmitResponse> submit( @RequestBody SubmitRequest submitRequest ) {
        if ( invalidLength( submitRequest ) ) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
        return new ResponseEntity<>( votingService.submitType( submitRequest ), HttpStatus.OK );
    }

    private boolean invalidLength( @RequestBody SubmitRequest submitRequest ) {
        ExceptionTypeWrapper submitted = submitRequest.getSubmittedType();
        return submitted.getDescription().length() > 1000 ||
                submitted.getShortName().length() > 100 ||
                submitted.getPrefix().length() > 200;
    }
}
