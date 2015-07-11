package com.attilapalf.exceptional.wrappers.notifications;

import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 212461305 on 2015.07.04..
 */
public class ExceptionNotification extends BaseNotification {

    private Data data;

    public ExceptionNotification (String toRegId, Users2ExceptionsEntity exception) {
        registration_ids = new ArrayList<>();
        registration_ids.add(toRegId);

        data = new Data();
        data.setTypeId(exception.getExceptionType().getTypeId());
        data.setInstanceId(exception.getU2EId());
        data.setFromWho(exception.getFromUser().getUserId());
        data.setToWho(exception.getToUser().getUserId());
        data.setTimeInMillis(exception.getCreationDate().getTime());
        data.setLongitude(exception.getLongitude());
        data.setLatitude(exception.getLatitude());
    }




    public static class Data extends BaseMessageData {

        private int typeId;
        private long instanceId;
        private double longitude;
        private double latitude;
        private long timeInMillis;
        private long fromWho;
        private long toWho;

        public Data() {
            notificationType = "exception";
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public long getInstanceId() {
            return instanceId;
        }

        public void setInstanceId(long instanceId) {
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


        public String getNotificationType() {
            return notificationType;
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
