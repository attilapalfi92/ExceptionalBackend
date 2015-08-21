package com.attilapalf.exceptional.entities;

import javax.persistence.*;

/**
 * Created by palfi on 2015-08-20.
 */
@Entity
@Table(name = "devices", schema = "", catalog = "exceptional")
public class DevicesEntity {
    private String deviceId;
    private String deviceName;
    private String gcmId;
    private UsersEntity user;

    @Id
    @Column(name = "device_id")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "device_name")
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Basic
    @Column(name = "gcm_id")
    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DevicesEntity that = (DevicesEntity) o;

        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
        if (deviceName != null ? !deviceName.equals(that.deviceName) : that.deviceName != null) return false;
        if (gcmId != null ? !gcmId.equals(that.gcmId) : that.gcmId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = deviceId != null ? deviceId.hashCode() : 0;
        result = 31 * result + (deviceName != null ? deviceName.hashCode() : 0);
        result = 31 * result + (gcmId != null ? gcmId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_fb_id", referencedColumnName = "facebook_id")
    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity users) {
        this.user = users;
    }
}
