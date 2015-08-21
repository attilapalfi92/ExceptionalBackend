package com.attilapalf.exceptional.wrappers;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by 212461305 on 2015.07.05..
 */
public class BaseExceptionRequestBody extends BaseRequestBody {
    protected List<BigInteger> knownExceptionIds;

    public List<BigInteger> getKnownExceptionIds() {
        return knownExceptionIds;
    }

    public void setKnownExceptionIds(List<BigInteger> knownExceptionIds) {
        this.knownExceptionIds = knownExceptionIds;
    }
}