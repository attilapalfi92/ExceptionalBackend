package com.attilapalf.exceptional.messages;

import com.attilapalf.exceptional.entities.BeingVotedExceptionType;

/**
 * Created by palfi on 2015-09-06.
 */
public class SubmitResponse {
    private ExceptionTypeWrapper submittedType;
    private boolean submittedThisWeek;

    public SubmitResponse( ) {
    }

    public SubmitResponse( ExceptionTypeWrapper submittedType, boolean submittedThisWeek ) {
        this.submittedType = submittedType;
        this.submittedThisWeek = submittedThisWeek;
    }

    public SubmitResponse( BeingVotedExceptionType exceptionType, boolean submittedThisWeek ) {
        this.submittedType = new ExceptionTypeWrapper( exceptionType );
        this.submittedThisWeek = submittedThisWeek;
    }

    public ExceptionTypeWrapper getSubmittedType( ) {
        return submittedType;
    }

    public void setSubmittedType( ExceptionTypeWrapper submittedType ) {
        this.submittedType = submittedType;
    }

    public boolean isSubmittedThisWeek( ) {
        return submittedThisWeek;
    }

    public void setSubmittedThisWeek( boolean submittedThisWeek ) {
        this.submittedThisWeek = submittedThisWeek;
    }
}
