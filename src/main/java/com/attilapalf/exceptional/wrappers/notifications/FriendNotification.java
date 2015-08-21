package com.attilapalf.exceptional.wrappers.notifications;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by 212461305 on 2015.07.11..
 */
public class FriendNotification extends BaseNotification {

    private Data data;

    public FriendNotification(BigInteger friendId, List<String> regIds) {
        data = new Data(friendId);
        registration_ids = regIds;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private static class Data extends BaseMessageData {
        private BigInteger friendId;

        public Data(BigInteger friendId) {
            this.friendId = friendId;
            notificationType = "friend";
        }

        public BigInteger getFriendId() {
            return friendId;
        }

        public void setFriendId(BigInteger friendId) {
            this.friendId = friendId;
        }
    }
}
