package com.attilapalf.exceptional.wrappers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Attila on 2015-06-11.
 */
public class RegistrationRequestBody {
    private BigInteger deviceId, userId;
    private List<BigInteger> friendsIds;

    public RegistrationRequestBody() {
    }

    public RegistrationRequestBody(BigInteger deviceId, BigInteger userId, List<BigInteger> friendsIds) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.friendsIds = friendsIds;
    }

    public BigInteger getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(BigInteger deviceId) {
        this.deviceId = deviceId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public List<BigInteger> getFriendsIds() {
        return friendsIds;
    }

    public void setFriendsIds(List<BigInteger> friendsIds) {
        this.friendsIds = friendsIds;
    }
}
