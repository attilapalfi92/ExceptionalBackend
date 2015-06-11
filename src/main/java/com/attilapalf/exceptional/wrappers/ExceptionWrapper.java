package com.attilapalf.exceptional.wrappers;

import java.math.BigInteger;
import java.util.Calendar;

/**
 * Created by Attila on 2015-06-11.
 */
public class ExceptionWrapper {
    private BigInteger fromWho, toWho;
    private Calendar creationDate;
    double longitude, latitude;
    int exceptionTypeId;

    public ExceptionWrapper() {
    }

    public ExceptionWrapper(BigInteger fromWho, BigInteger toWho, Calendar creationDate,
                            double longitude, double latitude, int exceptionTypeId) {
        this.fromWho = fromWho;
        this.toWho = toWho;
        this.creationDate = creationDate;
        this.longitude = longitude;
        this.latitude = latitude;
        this.exceptionTypeId = exceptionTypeId;
    }

    public BigInteger getFromWho() {
        return fromWho;
    }

    public void setFromWho(BigInteger fromWho) {
        this.fromWho = fromWho;
    }

    public BigInteger getToWho() {
        return toWho;
    }

    public void setToWho(BigInteger toWho) {
        this.toWho = toWho;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getExceptionTypeId() {
        return exceptionTypeId;
    }

    public void setExceptionTypeId(int exceptionTypeId) {
        this.exceptionTypeId = exceptionTypeId;
    }
}
