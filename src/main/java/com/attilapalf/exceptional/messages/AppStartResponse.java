package com.attilapalf.exceptional.messages;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Attila on 2015-06-11.
 */
public class AppStartResponse {
    private List<ExceptionInstanceWrapper> myExceptions;
    private List<ExceptionTypeWrapper> exceptionTypes;
    private List<ExceptionTypeWrapper> beingVotedTypes;
    private Map<BigInteger, Integer> friendsPoints;
    private int points;
    private int exceptionVersion;
    private boolean submittedThisWeek = false;
    private boolean votedThisWeek = false;

    public AppStartResponse() {
        myExceptions = new ArrayList<>();
        exceptionTypes = new ArrayList<>();
        beingVotedTypes = new ArrayList<>();
        friendsPoints = new HashMap<>();
    }

    public List<ExceptionInstanceWrapper> getMyExceptions() {
        return myExceptions;
    }

    public void setMyExceptions(List<ExceptionInstanceWrapper> myExceptions) {
        this.myExceptions = myExceptions;
    }

    public List<ExceptionTypeWrapper> getExceptionTypes() {
        return exceptionTypes;
    }

    public void setExceptionTypes(List<ExceptionTypeWrapper> exceptionTypes) {
        this.exceptionTypes = exceptionTypes;
    }

    public List<ExceptionTypeWrapper> getBeingVotedTypes() {
        return beingVotedTypes;
    }

    public void setBeingVotedTypes(List<ExceptionTypeWrapper> beingVotedTypes) {
        this.beingVotedTypes = beingVotedTypes;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getExceptionVersion() {
        return exceptionVersion;
    }

    public void setExceptionVersion(int exceptionVersion) {
        this.exceptionVersion = exceptionVersion;
    }

    public Map<BigInteger, Integer> getFriendsPoints() {
        return friendsPoints;
    }

    public void setFriendsPoints(Map<BigInteger, Integer> friendsPoints) {
        this.friendsPoints = friendsPoints;
    }

    public boolean isSubmittedThisWeek() {
        return submittedThisWeek;
    }

    public void setSubmittedThisWeek(boolean submittedThisWeek) {
        this.submittedThisWeek = submittedThisWeek;
    }

    public boolean isVotedThisWeek() {
        return votedThisWeek;
    }

    public void setVotedThisWeek(boolean votedThisWeek) {
        this.votedThisWeek = votedThisWeek;
    }
}
