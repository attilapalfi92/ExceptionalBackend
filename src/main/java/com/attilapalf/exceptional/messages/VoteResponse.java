package com.attilapalf.exceptional.messages;

import java.util.ArrayList;

/**
 * Created by palfi on 2015-09-06.
 */
public class VoteResponse {
    private boolean votedForThisWeek;
    private ExceptionTypeWrapper votedType;

    public VoteResponse( ) {
    }

    public VoteResponse( boolean votedForThisWeek, ExceptionTypeWrapper votedType ) {
        this.votedForThisWeek = votedForThisWeek;
        this.votedType = votedType;
    }

    public boolean isVotedForThisWeek( ) {
        return votedForThisWeek;
    }

    public void setVotedForThisWeek( boolean votedForThisWeek ) {
        this.votedForThisWeek = votedForThisWeek;
    }

    public ExceptionTypeWrapper getVotedType( ) {
        return votedType;
    }

    public void setVotedType( ExceptionTypeWrapper votedType ) {
        this.votedType = votedType;
    }
}
