package com.attilapalf.exceptional.messages.notifications;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by 212461305 on 2015.07.11..
 */
public class FriendNotification extends BaseNotification {
    private Data data;

    public FriendNotification(BigInteger friendId, String fullName, List<String> regIds) {
        data = new Data(friendId, fullName);
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
        private String fullName;

        public Data(BigInteger friendId, String fullName) {
            this.friendId = friendId;
            this.fullName = fullName;
            notificationType = "friend";
        }

        public BigInteger getFriendId() {
            return friendId;
        }

        public void setFriendId(BigInteger friendId) {
            this.friendId = friendId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }
}
