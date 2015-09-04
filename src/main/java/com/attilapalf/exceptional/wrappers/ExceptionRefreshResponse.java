package com.attilapalf.exceptional.wrappers;

import com.attilapalf.exceptional.entities.ExceptionInstancesEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 212461305 on 2015.07.05..
 */
public class ExceptionRefreshResponse {
    List<ExceptionInstanceWrapper> exceptionList;

    public ExceptionRefreshResponse() {
    }

    public ExceptionRefreshResponse(List<ExceptionInstancesEntity> exceptions) {
        exceptionList = new ArrayList<>(exceptions.size());

        for (ExceptionInstancesEntity e : exceptions) {
            exceptionList.add(new ExceptionInstanceWrapper(e));
        }
    }

    public List<ExceptionInstanceWrapper> getExceptionList() {
        return exceptionList;
    }

    public void setExceptionList(List<ExceptionInstanceWrapper> exceptionList) {
        this.exceptionList = exceptionList;
    }
}