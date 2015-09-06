package com.attilapalf.exceptional.messages;

import java.math.BigInteger;

/**
 * Created by 212461305 on 2015.07.11..
 */
public abstract class BaseRequest {
    protected BigInteger userFacebookId;

    public BigInteger getUserFacebookId() {
        return userFacebookId;
    }

    public void setUserFacebookId(BigInteger userFacebookId) {
        this.userFacebookId = userFacebookId;
    }
}
