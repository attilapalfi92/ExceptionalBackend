package com.attilapalf.exceptional.messages.notifications;

import java.util.List;

/**
 * Created by 212461305 on 2015.07.11..
 */
public abstract class BaseNotification {

    protected List<String> registration_ids;

    public List<String> getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(List<String> registration_ids) {
        this.registration_ids = registration_ids;
    }
}
