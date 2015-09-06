package com.attilapalf.exceptional.messages;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by 212461305 on 2015.07.05..
 */
public class BaseExceptionRequest extends BaseRequest {
    protected List<BigInteger> knownExceptionIds;

    public List<BigInteger> getKnownExceptionIds() {
        return knownExceptionIds;
    }

    public void setKnownExceptionIds(List<BigInteger> knownExceptionIds) {
        this.knownExceptionIds = knownExceptionIds;
    }
}