package com.attilapalf.exceptional.wrappers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Attila on 2015-06-11.
 */
public class RegistrationResponseBody {
    private List<BigInteger> myFriends;
    private List<ExceptionWrapper> myExceptions;

    public RegistrationResponseBody() {
        myFriends = new ArrayList<>();
        myExceptions = new ArrayList<>();
    }

    public List<BigInteger> getMyFriends() {
        return myFriends;
    }

    public void setMyFriends(List<BigInteger> myFriends) {
        this.myFriends = myFriends;
    }

    public List<ExceptionWrapper> getMyExceptions() {
        return myExceptions;
    }

    public void setMyExceptions(List<ExceptionWrapper> myExceptions) {
        this.myExceptions = myExceptions;
    }
}
