package com.attilapalf.exceptional.wrappers;

import com.attilapalf.exceptional.entities.Users2ExceptionsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 212461305 on 2015.07.05..
 */
public class ExceptionRefreshResponse {
    List<ExceptionWrapper> neededExceptions;

    public ExceptionRefreshResponse() {
    }

    public ExceptionRefreshResponse(List<Users2ExceptionsEntity> exceptions) {
        neededExceptions = new ArrayList<>(exceptions.size());

        for (Users2ExceptionsEntity e : exceptions) {
            neededExceptions.add(new ExceptionWrapper(e));
        }
    }

    public List<ExceptionWrapper> getNeededExceptions() {
        return neededExceptions;
    }

    public void setNeededExceptions(List<ExceptionWrapper> neededExceptions) {
        this.neededExceptions = neededExceptions;
    }
}