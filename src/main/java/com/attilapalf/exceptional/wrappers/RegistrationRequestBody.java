package com.attilapalf.exceptional.wrappers;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Created by Attila on 2015-06-11.
 */
public class RegistrationRequestBody {
    private long deviceId, userId;
    private Collection<Long> friendsIds;

    public RegistrationRequestBody() {
    }

    public RegistrationRequestBody(long deviceId, long userId, List<Long> friendsIds) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.friendsIds = friendsIds;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Collection<Long> getFriendsIds() {
        return friendsIds;
    }

    public void setFriendsIds(Collection<Long> friendsIds) {
        this.friendsIds = friendsIds;
    }
}
