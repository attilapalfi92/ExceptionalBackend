package com.attilapalf.exceptional.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Attila on 2015-06-10.
 */
@Entity
@Table(name = "users", schema = "", catalog = "exceptional")
public class UsersEntity {

    private long userId;
    private long userDbId;
    private String gcmId;
    private Collection<DevicesEntity> devices;

    public UsersEntity() {
        devices = new ArrayList<>();
    }

    @Id
    @Column(name = "user_id", columnDefinition = "BIGINT(255)", nullable = false, insertable = true, updatable = true)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "gcm_id", columnDefinition = "VARCHAR(255)", nullable = false, insertable = true, updatable = true, unique = true)
    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    @GeneratedValue
    @Column(name = "user_db_id", columnDefinition = "BIGINT(255)", nullable = false, updatable = true, insertable = false, unique = true)
    public long getUserDbId() {
        return userDbId;
    }

    public void setUserDbId(long userDbId) {
        this.userDbId = userDbId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (userId != that.userId) return false;

        return true;
    }

    @OneToMany(mappedBy = "user")
    public Collection<DevicesEntity> getDevices() {
        return devices;
    }

    public void setDevices(Collection<DevicesEntity> devicesesByUserId) {
        this.devices = devicesesByUserId;
    }


    public boolean hasDevice(String deviceId) {
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
