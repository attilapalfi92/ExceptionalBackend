package com.attilapalf.exceptional.wrappers;

import com.attilapalf.exceptional.entities.ExceptionTypesEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Attila on 2015-06-11.
 */
public class AppStartResponseBody {
    private List<ExceptionInstanceWrapper> myExceptions;
    private List<ExceptionTypeWrapper> exceptionTypes;
    private List<ExceptionTypeWrapper> beingVotedTypes;
    private int points;
    private int exceptionVersion;

    public AppStartResponseBody() {
        myExceptions = new ArrayList<>();
        exceptionTypes = new ArrayList<>();
        beingVotedTypes = new ArrayList<>();
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
}
