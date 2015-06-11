package com.attilapalf.exceptional.entities;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by Attila on 2015-06-11.
 */
@Entity
@Table(name = "devices", schema = "", catalog = "exceptional")
public class DevicesEntity {
    private long deviceId;
    private UsersEntity user;

    @Id
    @Column(name = "device_id", columnDefinition = "BIGINT(255)", nullable = false, insertable = true, updatable = true)
    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DevicesEntity that = (DevicesEntity) o;

        if (deviceId != that.deviceId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int)deviceId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity usersByUserId) {
        this.user = usersByUserId;
    }
}
