package com.attilapalf.exceptional.wrappers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Attila on 2015-06-11.
 */
public class RegistrationResponseBody {
    private Collection<ExceptionWrapper> myExceptions;

    public RegistrationResponseBody() {}

    public Collection<ExceptionWrapper> getMyExceptions() {
        return myExceptions;
    }

    public void setMyExceptions(Collection<ExceptionWrapper> myExceptions) {
        this.myExceptions = myExceptions;
    }
}
