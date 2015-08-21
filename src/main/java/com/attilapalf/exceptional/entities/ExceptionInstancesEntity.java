package com.attilapalf.exceptional.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by palfi on 2015-08-20.
 */
@Entity
@Table(name = "exception_instances", schema = "", catalog = "exceptional")
public class ExceptionInstancesEntity {
    private BigInteger id;
    private double longitude;
    private double latitude;
    private Timestamp dateTime;
    private UsersEntity fromUser;
    private UsersEntity toUser;
    private ExceptionTypesEntity type;

    @Id
    @Column(name = "id", columnDefinition = "BIGINT(255)")
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "longitude")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "date_time")
    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExceptionInstancesEntity that = (ExceptionInstancesEntity) o;

        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "from_user_fb_id", referencedColumnName = "facebook_id", nullable = false)
    public UsersEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UsersEntity fromUser) {
        this.fromUser = fromUser;
    }

    @ManyToOne
    @JoinColumn(name = "to_user_fb_id", referencedColumnName = "facebook_id", nullable = false)
    public UsersEntity getToUser() {
        return toUser;
    }

    public void setToUser(UsersEntity toUserId) {
        this.toUser = toUserId;
    }

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    public ExceptionTypesEntity getType() {
        return type;
    }

    public void setType(ExceptionTypesEntity type) {
        this.type = type;
    }
}
