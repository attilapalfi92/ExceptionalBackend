package com.attilapalf.exceptional.entities;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by Attila on 2015-06-11.
 */
@Entity
@Table(name = "devices", schema = "", catalog = "exceptional")
public class DevicesEntity {
    private BigInteger deviceId;
    private UsersEntity user;

    @Id
    @Column(name = "device_id", columnDefinition = "INT(20)", nullable = false, insertable = true, updatable = true)
    public BigInteger getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(BigInteger deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DevicesEntity that = (DevicesEntity) o;

        if (!deviceId.equals(that.deviceId)) return false;

        return true;
    }

//    @Override
//    public int hashCode() {
//        return deviceId;
//    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity usersByUserId) {
        this.user = usersByUserId;
    }
}
