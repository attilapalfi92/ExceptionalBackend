package com.attilapalf.exceptional.services;

import com.attilapalf.exceptional.entities.BeingVotedExceptionTypeEntity;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.being_voted_exception_types.BeingVotedCrud;
import com.attilapalf.exceptional.repositories.users.UserCrud;
import com.attilapalf.exceptional.messages.ExceptionTypeWrapper;
import com.attilapalf.exceptional.messages.VoteRequest;
import com.attilapalf.exceptional.messages.VoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by palfi on 2015-09-06.
 */
@Service
public class VotingService {

    @Autowired
    private UserCrud userCrud;
    @Autowired
    private BeingVotedCrud beingVotedCrud;

    public VoteResponse voteForType(VoteRequest voteRequest) {
        UsersEntity voter = userCrud.findOne(voteRequest.getUserId());
        BeingVotedExceptionTypeEntity votedType = beingVotedCrud.findOne(voteRequest.getVotedExceptionId());
        if (voter.getVotedForException() == null) {
            changeAffectedEntities(voter, votedType);
        }
        return new VoteResponse(true, new ExceptionTypeWrapper(votedType));
    }

    private void changeAffectedEntities(UsersEntity voter, BeingVotedExceptionTypeEntity votedType) {
        voter.setVotedForException(votedType);
        votedType.setVotes(votedType.getVotes() + 1);
        userCrud.save(voter);
        beingVotedCrud.save(votedType);
    }
}
