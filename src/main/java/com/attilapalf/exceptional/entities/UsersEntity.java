package com.attilapalf.exceptional.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by palfi on 2015-08-20.
 */
@Entity
@Table(name = "users", schema = "", catalog = "exceptional")
public class UsersEntity {
    private BigInteger dbId;
    private BigInteger facebookId;
    private int points;
    private String firstName;
    private String lastName;
    private List<DevicesEntity> devices;
    private BeingVotedExceptionTypeEntity votedForException;
    private List<ExceptionTypesEntity> MyWinnerExceptions;
    private List<ExceptionInstancesEntity> sentByMe;
    private List<ExceptionInstancesEntity> sentToMe;
    private BeingVotedExceptionTypeEntity MySubmissionForVote;

    @Id
    @Column(name = "facebook_id", columnDefinition = "BIGINT(255)")
    public BigInteger getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(BigInteger facebookId) {
        this.facebookId = facebookId;
    }

    @Basic
    @GeneratedValue
    @Column(name = "db_id", columnDefinition = "BIGINT(255)")
    public BigInteger getDbId() {
        return dbId;
    }

    public void setDbId(BigInteger dbId) {
        this.dbId = dbId;
    }

    @Basic
    @Column(name = "points")
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (points != that.points) return false;
        if (dbId != null ? !dbId.equals(that.dbId) : that.dbId != null) return false;
        if (facebookId != null ? !facebookId.equals(that.facebookId) : that.facebookId != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dbId != null ? dbId.hashCode() : 0;
        result = 31 * result + (facebookId != null ? facebookId.hashCode() : 0);
        result = 31 * result + points;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "user")
    public List<DevicesEntity> getDevices() {
        return devices;
    }

    public void setDevices(List<DevicesEntity> devices) {
        this.devices = devices;
    }

    @ManyToOne
    @JoinColumn(name = "voted_for_exception", referencedColumnName = "id")
    public BeingVotedExceptionTypeEntity getVotedForException() {
        return votedForException;
    }

    public void setVotedForException(BeingVotedExceptionTypeEntity votedForException) {
        this.votedForException = votedForException;
    }

    @OneToMany(mappedBy = "submittedBy")
    public List<ExceptionTypesEntity> getMyWinnerExceptions() {
        return MyWinnerExceptions;
    }

    public void setMyWinnerExceptions(List<ExceptionTypesEntity> myWinnerExceptions) {
        MyWinnerExceptions = myWinnerExceptions;
    }

    @OneToMany(mappedBy = "fromUser")
    public List<ExceptionInstancesEntity> getSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(List<ExceptionInstancesEntity> sentByMe) {
        this.sentByMe = sentByMe;
    }

    @OneToMany(mappedBy = "toUser")
    public List<ExceptionInstancesEntity> getSentToMe() {
        return sentToMe;
    }

    public void setSentToMe(List<ExceptionInstancesEntity> sentToMe) {
        this.sentToMe = sentToMe;
    }

    @OneToOne(mappedBy = "submittedBy")
    public BeingVotedExceptionTypeEntity getMySubmissionForVote() {
        return MySubmissionForVote;
    }

    public void setMySubmissionForVote(BeingVotedExceptionTypeEntity mySubmissionForVote) {
        MySubmissionForVote = mySubmissionForVote;
    }
}
