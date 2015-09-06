package com.attilapalf.exceptional.messages;

import java.math.BigInteger;

/**
 * Created by palfi on 2015-09-06.
 */
public class SubmitRequest {
    private BigInteger submitterId;
    private ExceptionTypeWrapper submittedType;

    public SubmitRequest() {
    }

    public SubmitRequest(BigInteger submitterId, ExceptionTypeWrapper submittedType) {
        this.submitterId = submitterId;
        this.submittedType = submittedType;
    }

    public BigInteger getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(BigInteger submitterId) {
        this.submitterId = submitterId;
    }

    public ExceptionTypeWrapper getSubmittedType() {
        return submittedType;
    }

    public void setSubmittedType(ExceptionTypeWrapper submittedType) {
        this.submittedType = submittedType;
    }
}
