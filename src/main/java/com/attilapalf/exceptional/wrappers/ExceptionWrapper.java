package com.attilapalf.exceptional.wrappers;

import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;

/**
 * Created by Attila on 2015-06-11.
 */
public class ExceptionWrapper {
    private long fromWho, toWho;
    private long timeInMillis;
    private double longitude, latitude;
    private int exceptionTypeId;
    private long instanceId;

    public ExceptionWrapper() {
    }

    public ExceptionWrapper(Users2ExceptionsEntity exception) {
        fromWho = exception.getFromUser().getUserId();
        toWho = exception.getToUser().getUserId();
        timeInMillis = exception.getCreationDate().getTime();
        longitude = exception.getLongitude();
        latitude = exception.getLatitude();
        exceptionTypeId = exception.getExceptionType().getTypeId();
        instanceId = exception.getU2EId();
    }

    public ExceptionWrapper(Long fromWho, Long toWho, long timeInMillis,
                            double longitude, double latitude, int exceptionTypeId, long instanceId) {
        this.fromWho = fromWho;
        this.toWho = toWho;
        this.timeInMillis = timeInMillis;
        this.longitude = longitude;
        this.latitude = latitude;
        this.exceptionTypeId = exceptionTypeId;
        this.instanceId = instanceId;
    }

    public long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId;
    }

    public long getFromWho() {
        return fromWho;
    }

    public void setFromWho(long fromWho) {
        this.fromWho = fromWho;
    }

    public long getToWho() {
        return toWho;
    }

    public void setToWho(long toWho) {
        this.toWho = toWho;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
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
