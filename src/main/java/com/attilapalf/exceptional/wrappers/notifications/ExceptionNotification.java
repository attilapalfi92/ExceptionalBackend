package com.attilapalf.exceptional.wrappers.notifications;

import com.attilapalf.exceptional.entities.ExceptionInstancesEntity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by 212461305 on 2015.07.04..
 */
public class ExceptionNotification extends BaseNotification {

    private Data data;

    public ExceptionNotification(List<String> toGcmIds, ExceptionInstancesEntity exception, int points) {
        registration_ids = toGcmIds;
        data = new Data();
        data.setTypeId(exception.getType().getId());
        data.setInstanceId(exception.getId());
        data.setFromWho(exception.getFromUser().getFacebookId());
        data.setToWho(exception.getToUser().getFacebookId());
        data.setTimeInMillis(exception.getDateTime().getTime());
        data.setLongitude(exception.getLongitude());
        data.setLatitude(exception.getLatitude());
        data.setPoints(points);
    }






    public static class Data extends BaseMessageData {

        private int typeId;
        private BigInteger instanceId;
        private double longitude;
        private double latitude;
        private long timeInMillis;
        private BigInteger fromWho;
        private BigInteger toWho;
        private int points;

        public Data() {
            notificationType = "exception";
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public BigInteger getInstanceId() {
            return instanceId;
        }

        public void setInstanceId(BigInteger instanceId) {
            this.instanceId = instanceId;
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

        public long getTimeInMillis() {
            return timeInMillis;
        }

        public void setTimeInMillis(long timeInMillis) {
            this.timeInMillis = timeInMillis;
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

        public String getNotificationType() {
            return notificationType;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }
    }

    public List<String> getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(List<String> registration_ids) {
        this.registration_ids = registration_ids;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
