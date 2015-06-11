package com.attilapalf.exceptional.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Attila on 2015-06-10.
 */
@Entity
@Table(name = "users", schema = "", catalog = "exceptional")
public class UsersEntity {

    private BigInteger userId;
    private Collection<DevicesEntity> devices;

    public UsersEntity() {
        devices = new ArrayList<>();
    }

    @Id
    @Column(name = "user_id", columnDefinition = "INT(20)", nullable = false, insertable = true, updatable = true)
    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (!userId.equals(that.userId)) return false;

        return true;
    }

    @OneToMany(mappedBy = "user")
    public Collection<DevicesEntity> getDevices() {
        return devices;
    }

    public void setDevices(Collection<DevicesEntity> devicesesByUserId) {
        this.devices = devicesesByUserId;
    }


    public boolean hasDevice(BigInteger deviceId) {
        for (DevicesEntity d : devices) {
            if (d.getDeviceId().equals(deviceId)) {
                return true;
            }
        }

        return false;
    }

    public void addDevice(DevicesEntity device) {
        devices.add(device);
    }
}
