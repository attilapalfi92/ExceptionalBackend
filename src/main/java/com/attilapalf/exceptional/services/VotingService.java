package com.attilapalf.exceptional.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attilapalf.exceptional.entities.BeingVotedExceptionTypeEntity;
import com.attilapalf.exceptional.entities.ExceptionTypesEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
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
        UsersEntity voter = userCrud.findOne( voteRequest.getUserId() );
        BeingVotedExceptionTypeEntity votedType = beingVotedCrud.findOne( voteRequest.getVotedExceptionId() );
        if ( voter.getVotedForException() == null ) {
            changeAffectedEntities( voter, votedType );
        }
        return new VoteResponse( true, new ExceptionTypeWrapper( votedType ) );
    }

    private void changeAffectedEntities( UsersEntity voter, BeingVotedExceptionTypeEntity votedType ) {
        voter.setVotedForException( votedType );
        votedType.setVotes( votedType.getVotes() + 1 );
        userCrud.save( voter );
        beingVotedCrud.save( votedType );
    }

    @Transactional
    public SubmitResponse submitType( SubmitRequest submitRequest ) {
        UsersEntity submitter = userCrud.findOne( submitRequest.getSubmitterId() );
        if ( submitter.getMySubmissionForVote() == null ) {
            ExceptionTypeWrapper wrapper = submitRequest.getSubmittedType();
            BeingVotedExceptionTypeEntity exceptionType = createBeingVotedExceptionType( submitter, wrapper );
            beingVotedCrud.save( exceptionType );
            return new SubmitResponse( exceptionType, true );
        }
        return new SubmitResponse( (ExceptionTypeWrapper) null, true );
    }

    private BeingVotedExceptionTypeEntity createBeingVotedExceptionType( UsersEntity submitter, ExceptionTypeWrapper wrapper ) {
        BeingVotedExceptionTypeEntity exceptionType = new BeingVotedExceptionTypeEntity();
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
    protected void resetVoting( ) {
        BeingVotedExceptionTypeEntity winner = beingVotedCrud.findWinner();
        if ( winner != null ) {
            ExceptionTypesEntity exceptionType = createExceptionTypesEntity( winner );
            exceptionTypeCrud.save( exceptionType );
            beingVotedCrud.deleteAll();
            constantCrud.incrementExceptionVersion();
        }
    }

    private ExceptionTypesEntity createExceptionTypesEntity( BeingVotedExceptionTypeEntity winner ) {
        ExceptionTypesEntity exceptionType = new ExceptionTypesEntity();
        exceptionType.setType( "SUBMITTED" );
        exceptionType.setSubmittedBy( winner.getSubmittedBy() );
        exceptionType.setPrefix( winner.getPrefix() );
        exceptionType.setShortName( winner.getShortName() );
        exceptionType.setDescription( winner.getDescription() );
        exceptionType.setVersion( winner.getVersion() );
        return exceptionType;
    }
}
