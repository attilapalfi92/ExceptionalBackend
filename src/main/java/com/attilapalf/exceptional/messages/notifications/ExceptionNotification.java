package com.attilapalf.exceptional.messages.notifications;

import java.math.BigInteger;
import java.util.List;

import com.attilapalf.exceptional.entities.ExceptionInstance;
import com.attilapalfi.exceptional.model.Question;

/**
 * Created by 212461305 on 2015.07.04..
 */
public class ExceptionNotification extends BaseNotification {

    private Data data;

    public ExceptionNotification( List<String> toGcmIds, ExceptionInstance exception, int yourPoints,
                                  int friendPoints, Question question ) {
        registration_ids = toGcmIds;
        data = new Data();
        data.setTypeId( exception.getType().getId() );
        data.setInstanceId( exception.getId() );
        data.setFromWho( exception.getFromUser().getFacebookId() );
        data.setToWho( exception.getToUser().getFacebookId() );
        data.setTimeInMillis( exception.getDateTime().getTime() );
        data.setLongitude( exception.getLongitude() );
        data.setLatitude( exception.getLatitude() );
        data.setYourPoints( yourPoints );
        data.setFriendPoints( friendPoints );
        data.setExceptionPointsForSender( exception.getPointsForSender() );
        data.setExceptionPointsForReceiver( exception.getPointsForReceiver() );
        data.setCity( exception.getCity() );
        data.setQuestionText( question.getText() );
        data.setHasQuestion( question.getHasQuestion() );
        data.setYesIsCorrect( question.getYesIsCorrect() );
    }

    public static class Data extends BaseMessageData {
        private int typeId;
        private BigInteger instanceId;
        private double longitude;
        private double latitude;
        private long timeInMillis;
        private BigInteger fromWho;
        private BigInteger toWho;
        private int yourPoints;
        private int friendPoints;
        private String questionText;
        private boolean yesIsCorrect;
        private boolean hasQuestion;
        private int exceptionPointsForSender;
        private int exceptionPointsForReceiver;
        private String city;

        public Data( ) {
            notificationType = "exception";
        }

        public int getTypeId( ) {
            return typeId;
        }

        public void setTypeId( int typeId ) {
            this.typeId = typeId;
        }

        public BigInteger getInstanceId( ) {
            return instanceId;
        }

        public void setInstanceId( BigInteger instanceId ) {
            this.instanceId = instanceId;
        }

        public double getLongitude( ) {
            return longitude;
        }

        public void setLongitude( double longitude ) {
            this.longitude = longitude;
        }

        public double getLatitude( ) {
            return latitude;
        }

        public void setLatitude( double latitude ) {
            this.latitude = latitude;
        }

        public long getTimeInMillis( ) {
            return timeInMillis;
        }

        public void setTimeInMillis( long timeInMillis ) {
            this.timeInMillis = timeInMillis;
        }

        public BigInteger getFromWho( ) {
            return fromWho;
        }

        public void setFromWho( BigInteger fromWho ) {
            this.fromWho = fromWho;
        }

        public BigInteger getToWho( ) {
            return toWho;
        }

        public void setToWho( BigInteger toWho ) {
            this.toWho = toWho;
        }

        public String getNotificationType( ) {
            return notificationType;
        }

        public int getYourPoints( ) {
            return yourPoints;
        }

        public void setYourPoints( int yourPoints ) {
            this.yourPoints = yourPoints;
        }

        public int getFriendPoints( ) {
            return friendPoints;
        }

        public void setFriendPoints( int friendPoints ) {
            this.friendPoints = friendPoints;
        }

        public String getQuestionText( ) {
            return questionText;
        }

        public void setQuestionText( String questionText ) {
            this.questionText = questionText;
        }

        public boolean isYesIsCorrect( ) {
            return yesIsCorrect;
        }

        public void setYesIsCorrect( boolean yesIsCorrect ) {
            this.yesIsCorrect = yesIsCorrect;
        }

        public boolean isHasQuestion( ) {
            return hasQuestion;
        }

        public void setHasQuestion( boolean hasQuestion ) {
            this.hasQuestion = hasQuestion;
        }

        public int getExceptionPointsForSender( ) {
            return exceptionPointsForSender;
        }

        public void setExceptionPointsForSender( int exceptionPointsForSender ) {
            this.exceptionPointsForSender = exceptionPointsForSender;
        }

        public int getExceptionPointsForReceiver( ) {
            return exceptionPointsForReceiver;
        }

        public void setExceptionPointsForReceiver( int exceptionPointsForReceiver ) {
            this.exceptionPointsForReceiver = exceptionPointsForReceiver;
        }

        public String getCity( ) {
            return city;
        }

        public void setCity( String city ) {
            this.city = city;
        }
    }

    public List<String> getRegistration_ids( ) {
        return registration_ids;
    }

    public void setRegistration_ids( List<String> registration_ids ) {
        this.registration_ids = registration_ids;
    }

    public Data getData( ) {
        return data;
    }

    public void setData( Data data ) {
        this.data = data;
    }
}
