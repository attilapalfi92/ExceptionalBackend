package com.attilapalf.exceptional.wrappers.notifications;

import java.util.List;

/**
 * Created by 212461305 on 2015.07.11..
 */
public class FriendNotification extends BaseNotification {

    private Data data;

    public FriendNotification(long friendId, List<String> regIds) {
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
        private long friendId;

        public Data(long friendId) {
            this.friendId = friendId;
            notificationType = "friend";
        }

        public long getFriendId() {
            return friendId;
        }

        public void setFriendId(long friendId) {
            this.friendId = friendId;
        }
    }
}
