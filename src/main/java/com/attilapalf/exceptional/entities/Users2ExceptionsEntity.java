package com.attilapalf.exceptional.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by Attila on 2015-06-11.
 */
@Entity
@Table(name = "users_2_exceptions", schema = "", catalog = "exceptional")
public class Users2ExceptionsEntity {
    private BigInteger u2EId;
    private double longitude;
    private double latitude;
    private Timestamp creationDate;
    private ExceptionsEntity exception;
    private UsersEntity fromUser;
    private UsersEntity toUser;

    @Id
    @Column(name = "u2e_id", columnDefinition = "INT(20)", nullable = false, insertable = true, updatable = true)
    public BigInteger getU2EId() {
        return u2EId;
    }

    public void setU2EId(BigInteger u2EId) {
        this.u2EId = u2EId;
    }

    @Basic
    @Column(name = "longitude", nullable = false, insertable = true, updatable = true, precision = 0)
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude", nullable = false, insertable = true, updatable = true, precision = 0)
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "creation_date", nullable = false, insertable = true, updatable = true)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users2ExceptionsEntity that = (Users2ExceptionsEntity) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (!u2EId.equals(that.u2EId)) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

//    @Override
//    public int hashCode() {
//        int result;
//        long temp;
//        result = u2EId;
//        temp = Double.doubleToLongBits(longitude);
//        result = 31 * result + (int) (temp ^ (temp >>> 32));
//        temp = Double.doubleToLongBits(latitude);
//        result = 31 * result + (int) (temp ^ (temp >>> 32));
//        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
//        return result;
//    }

    @ManyToOne
    @JoinColumn(name = "exception", referencedColumnName = "type_id", nullable = false)
    public ExceptionsEntity getException() {
        return exception;
    }

    public void setException(ExceptionsEntity exceptionsByException) {
        this.exception = exceptionsByException;
    }

    @ManyToOne
    @JoinColumn(name = "from_user_id", referencedColumnName = "user_id")
    public UsersEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UsersEntity usersByFromUserId) {
        this.fromUser = usersByFromUserId;
    }

    @ManyToOne
    @JoinColumn(name = "to_user_id", referencedColumnName = "user_id")
    public UsersEntity getToUser() {
        return toUser;
    }

    public void setToUser(UsersEntity usersByToUserId) {
        this.toUser = usersByToUserId;
    }
}
