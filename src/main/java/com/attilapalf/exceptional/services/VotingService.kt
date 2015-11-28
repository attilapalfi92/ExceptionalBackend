package com.attilapalf.exceptional.services


import com.attilapalf.exceptional.entities.BeingVotedExceptionType
import com.attilapalf.exceptional.entities.ExceptionType
import com.attilapalf.exceptional.entities.User
import com.attilapalf.exceptional.messages.*
import com.attilapalf.exceptional.repositories.being_voted_exception_types.BeingVotedCrud
import com.attilapalf.exceptional.repositories.constants.ConstantCrud
import com.attilapalf.exceptional.repositories.exceptiontypes.ExceptionTypeCrud
import com.attilapalf.exceptional.repositories.users.UserCrud
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by palfi on 2015-09-06.
 */
public interface VotingService {
    fun voteForType(voteRequest: VoteRequest): VoteResponse
    fun submitType(submitRequest: SubmitRequest): SubmitResponse
}

@Service
class VotingServiceImpl : VotingService {

    @Autowired
    private lateinit var userCrud: UserCrud
    @Autowired
    private lateinit var beingVotedCrud: BeingVotedCrud
    @Autowired
    private lateinit var constantCrud: ConstantCrud
    @Autowired
    private lateinit var exceptionTypeCrud: ExceptionTypeCrud

    @Transactional
    override fun voteForType(voteRequest: VoteRequest): VoteResponse {
        val voter = userCrud.findOne(voteRequest.userId)
        val votedType = beingVotedCrud.findOne(voteRequest.votedExceptionId)
        if (voter.votedForException == null) {
            changeAffectedEntities(voter, votedType)
        }
        return VoteResponse(true, ExceptionTypeWrapper(votedType))
    }

    private fun changeAffectedEntities(voter: User, votedType: BeingVotedExceptionType) {
        voter.votedForException = votedType
        votedType.votes = votedType.votes + 1
        userCrud.save(voter)
        beingVotedCrud.save(votedType)
    }

    @Transactional
    override fun submitType(submitRequest: SubmitRequest): SubmitResponse {
        val submitter = userCrud.findOne(submitRequest.submitterId)
        if (submitter.mySubmissionForVote == null) {
            val wrapper = submitRequest.submittedType
            val exceptionType = createBeingVotedExceptionType(submitter, wrapper)
            beingVotedCrud.save(exceptionType)
            return SubmitResponse(exceptionType, true)
        }
        return SubmitResponse(true)
    }

    private fun createBeingVotedExceptionType(submitter: User, wrapper: ExceptionTypeWrapper): BeingVotedExceptionType {
        val exceptionType = BeingVotedExceptionType()
        exceptionType.submittedBy = submitter
        exceptionType.prefix = wrapper.prefix
        exceptionType.shortName = wrapper.shortName
        exceptionType.description = wrapper.description
        exceptionType.version = constantCrud.exceptionVersion + 1
        exceptionType.votes = 0
        return exceptionType
    }

    // second, minute, hour, day of month, month, day(s) of week
    @Scheduled(cron = "1 0 0 ? * MON")
    @Transactional
    fun resetVoting() {
        val winner = beingVotedCrud.findWinner()
        removeUserReferences()
        finishVotingReset(winner)
    }

    private fun removeUserReferences() {
        userCrud.findAll().forEach { user ->
            user.votedForException = null
            user.mySubmissionForVote = null
            userCrud.save(user)
        }
    }

    private fun createExceptionTypesEntity(winner: BeingVotedExceptionType): ExceptionType {
        val exceptionType = ExceptionType()
        exceptionType.type = "SUBMITTED"
        exceptionType.submittedBy = winner.submittedBy
        exceptionType.prefix = winner.prefix
        exceptionType.shortName = winner.shortName
        exceptionType.description = winner.description
        exceptionType.version = winner.version
        return exceptionType
    }

    private fun finishVotingReset(winner: BeingVotedExceptionType?) {
        if (winner != null) {
            val exceptionType = createExceptionTypesEntity(winner)
            exceptionTypeCrud.save(exceptionType)
            beingVotedCrud.deleteAll()
            constantCrud.incrementExceptionVersion()
        }
    }
}
