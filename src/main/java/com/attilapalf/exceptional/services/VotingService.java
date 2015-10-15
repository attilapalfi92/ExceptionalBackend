package com.attilapalf.exceptional.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attilapalf.exceptional.entities.BeingVotedExceptionType;
import com.attilapalf.exceptional.entities.ExceptionType;
import com.attilapalf.exceptional.entities.User;
import com.attilapalf.exceptional.messages.*;
import com.attilapalf.exceptional.repositories.being_voted_exception_types.BeingVotedCrud;
import com.attilapalf.exceptional.repositories.constants.ConstantCrud;
import com.attilapalf.exceptional.repositories.exceptiontypes.ExceptionTypeCrud;
import com.attilapalf.exceptional.repositories.users.UserCrud;

/**
 * Created by palfi on 2015-09-06.
 */
@Service
public class VotingService {

    @Autowired
    private UserCrud userCrud;
    @Autowired
    private BeingVotedCrud beingVotedCrud;
    @Autowired
    private ConstantCrud constantCrud;
    @Autowired
    private ExceptionTypeCrud exceptionTypeCrud;

    @Transactional
    public VoteResponse voteForType( VoteRequest voteRequest ) {
        User voter = userCrud.findOne( voteRequest.getUserId() );
        BeingVotedExceptionType votedType = beingVotedCrud.findOne( voteRequest.getVotedExceptionId() );
        if ( voter.getVotedForException() == null ) {
            changeAffectedEntities( voter, votedType );
        }
        return new VoteResponse( true, new ExceptionTypeWrapper( votedType ) );
    }

    private void changeAffectedEntities( User voter, BeingVotedExceptionType votedType ) {
        voter.setVotedForException( votedType );
        votedType.setVotes( votedType.getVotes() + 1 );
        userCrud.save( voter );
        beingVotedCrud.save( votedType );
    }

    @Transactional
    public SubmitResponse submitType( SubmitRequest submitRequest ) {
        User submitter = userCrud.findOne( submitRequest.getSubmitterId() );
        if ( submitter.getMySubmissionForVote() == null ) {
            ExceptionTypeWrapper wrapper = submitRequest.getSubmittedType();
            BeingVotedExceptionType exceptionType = createBeingVotedExceptionType( submitter, wrapper );
            beingVotedCrud.save( exceptionType );
            return new SubmitResponse( exceptionType, true );
        }
        return new SubmitResponse( (ExceptionTypeWrapper) null, true );
    }

    private BeingVotedExceptionType createBeingVotedExceptionType( User submitter, ExceptionTypeWrapper wrapper ) {
        BeingVotedExceptionType exceptionType = new BeingVotedExceptionType();
        exceptionType.setSubmittedBy( submitter );
        exceptionType.setPrefix( wrapper.getPrefix() );
        exceptionType.setShortName( wrapper.getShortName() );
        exceptionType.setDescription( wrapper.getDescription() );
        exceptionType.setVersion( constantCrud.getExceptionVersion() + 1 );
        exceptionType.setVotes( 0 );
        return exceptionType;
    }

    // second, minute, hour, day of month, month, day(s) of week
    @Scheduled( cron = "1 0 0 ? * MON" )
    @Transactional
    public void resetVoting( ) {
        BeingVotedExceptionType winner = beingVotedCrud.findWinner();
        removeUserReferences();
        finishVotingReset( winner );
    }

    private void removeUserReferences( ) {
        userCrud.findAll().forEach( user -> {
            user.setVotedForException( null );
            user.setMySubmissionForVote( null );
            userCrud.save( user );
        });
    }

    private ExceptionType createExceptionTypesEntity( BeingVotedExceptionType winner ) {
        ExceptionType exceptionType = new ExceptionType();
        exceptionType.setType( "SUBMITTED" );
        exceptionType.setSubmittedBy( winner.getSubmittedBy() );
        exceptionType.setPrefix( winner.getPrefix() );
        exceptionType.setShortName( winner.getShortName() );
        exceptionType.setDescription( winner.getDescription() );
        exceptionType.setVersion( winner.getVersion() );
        return exceptionType;
    }

    private void finishVotingReset( BeingVotedExceptionType winner ) {
        if ( winner != null ) {
            ExceptionType exceptionType = createExceptionTypesEntity( winner );
            exceptionTypeCrud.save( exceptionType );
            beingVotedCrud.deleteAll();
            constantCrud.incrementExceptionVersion();
        }
    }
}
