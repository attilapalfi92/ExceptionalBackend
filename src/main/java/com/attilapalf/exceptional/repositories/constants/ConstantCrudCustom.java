package com.attilapalf.exceptional.repositories.constants;

import java.math.BigInteger;

/**
 * Created by 212461305 on 2015.07.10..
 */
public interface ConstantCrudCustom {
    int getMaxExceptionsPerUser();
    int getExceptionVersion();
    int getStartingPoint();
    int getClientCacheSize();
    void incrementExceptionVersion();
}
