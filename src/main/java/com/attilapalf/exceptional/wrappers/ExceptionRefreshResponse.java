package com.attilapalf.exceptional.wrappers;

import com.attilapalf.exceptional.entities.ExceptionInstancesEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 212461305 on 2015.07.05..
 */
public class ExceptionRefreshResponse {
    List<ExceptionInstanceWrapper> neededExceptions;

    public ExceptionRefreshResponse() {
    }

    public ExceptionRefreshResponse(List<ExceptionInstancesEntity> exceptions) {
        neededExceptions = new ArrayList<>(exceptions.size());

        for (ExceptionInstancesEntity e : exceptions) {
            neededExceptions.add(new ExceptionInstanceWrapper(e));
        }
    }

    public List<ExceptionInstanceWrapper> getNeededExceptions() {
        return neededExceptions;
    }

    public void setNeededExceptions(List<ExceptionInstanceWrapper> neededExceptions) {
        this.neededExceptions = neededExceptions;
    }
}