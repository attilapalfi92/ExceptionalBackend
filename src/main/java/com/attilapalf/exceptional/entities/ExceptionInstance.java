package com.attilapalf.exceptional.entities;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * Created by palfi on 2015-08-20.
 */
@Entity
@Table( name = "exception_instances", schema = "", catalog = "exceptional" )
public class ExceptionInstance {
    private BigInteger id;
    private double longitude;
    private double latitude;
    private Timestamp dateTime;
    private User fromUser;
    private User toUser;
    private ExceptionType type;
    private String questionText;
    private boolean answered;
    private boolean hasQuestion;
    private boolean yesIsCorrect;
    private boolean answeredCorrectly;
    private int pointsForSender;
    private int pointsForReceiver;

    @Id
    @GeneratedValue
    @Column( name = "id", columnDefinition = "BIGINT(255)" )
    public BigInteger getId( ) {
        return id;
    }

    public void setId( BigInteger id ) {
        this.id = id;
    }

    @Basic
    @Column( name = "longitude" )
    public double getLongitude( ) {
        return longitude;
    }

    public void setLongitude( double longitude ) {
        this.longitude = longitude;
    }

    @Basic
    @Column( name = "latitude" )
    public double getLatitude( ) {
        return latitude;
    }

    public void setLatitude( double latitude ) {
        this.latitude = latitude;
    }

    @Basic
    @Column( name = "date_time" )
    public Timestamp getDateTime( ) {
        return dateTime;
    }

    public void setDateTime( Timestamp dateTime ) {
        this.dateTime = dateTime;
    }

    @Basic
    @Column( name = "question" )
    public String getQuestionText( ) {
        return questionText;
    }

    public void setQuestionText( String question ) {
        this.questionText = question;
    }

    @Basic
    @Column( name = "answered" )
    public boolean isAnswered( ) {
        return answered;
    }

    public void setAnswered( boolean answered ) {
        this.answered = answered;
    }

    @Basic
    @Column( name = "has_question" )
    public boolean isHasQuestion( ) {
        return hasQuestion;
    }

    public void setHasQuestion( boolean hasQuestion ) {
        this.hasQuestion = hasQuestion;
    }

    @Basic
    @Column( name = "yes_is_correct" )
    public boolean isYesIsCorrect( ) {
        return yesIsCorrect;
    }

    public void setYesIsCorrect( boolean yesIsCorrect ) {
        this.yesIsCorrect = yesIsCorrect;
    }

    @Basic
    @Column( name = "answered_correctly" )
    public boolean isAnsweredCorrectly( ) {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly( boolean answeredCorrectly ) {
        this.answeredCorrectly = answeredCorrectly;
    }

    @Basic
    @Column( name = "points_for_sender" )
    public int getPointsForSender( ) {
        return pointsForSender;
    }

    public void setPointsForSender( int pointsForSender ) {
        this.pointsForSender = pointsForSender;
    }

    @Basic
    @Column( name = "points_for_receiver" )
    public int getPointsForReceiver( ) {
        return pointsForReceiver;
    }

    public void setPointsForReceiver( int pointsForReceiver ) {
        this.pointsForReceiver = pointsForReceiver;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        ExceptionInstance that = (ExceptionInstance) o;

        if ( Double.compare( that.longitude, longitude ) != 0 ) return false;
        if ( Double.compare( that.latitude, latitude ) != 0 ) return false;
        if ( id != null ? !id.equals( that.id ) : that.id != null ) return false;
        if ( dateTime != null ? !dateTime.equals( that.dateTime ) : that.dateTime != null ) return false;

        return true;
    }

    @Override
    public int hashCode( ) {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        temp = Double.doubleToLongBits( longitude );
        result = 31 * result + (int) ( temp ^ ( temp >>> 32 ) );
        temp = Double.doubleToLongBits( latitude );
        result = 31 * result + (int) ( temp ^ ( temp >>> 32 ) );
        result = 31 * result + ( dateTime != null ? dateTime.hashCode() : 0 );
        return result;
    }

    @ManyToOne
    @JoinColumn( name = "from_user_fb_id", referencedColumnName = "facebook_id", nullable = false )
    public User getFromUser( ) {
        return fromUser;
    }

    public void setFromUser( User fromUser ) {
        this.fromUser = fromUser;
    }

    @ManyToOne
    @JoinColumn( name = "to_user_fb_id", referencedColumnName = "facebook_id", nullable = false )
    public User getToUser( ) {
        return toUser;
    }

    public void setToUser( User toUserId ) {
        this.toUser = toUserId;
    }

    @ManyToOne
    @JoinColumn( name = "type_id", referencedColumnName = "id", nullable = false )
    public ExceptionType getType( ) {
        return type;
    }

    public void setType( ExceptionType type ) {
        this.type = type;
    }
}
