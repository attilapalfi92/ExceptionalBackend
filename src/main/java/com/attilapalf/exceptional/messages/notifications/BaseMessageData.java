package com.attilapalf.exceptional.messages.notifications;

/**
 * Created by 212461305 on 2015.07.04..
 */
public abstract class BaseMessageData {
    protected String notificationType;

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
