package com.attilapalf.exceptional.controllers;

import com.attilapalf.exceptional.logic.ExceptionLogic;
import com.attilapalf.exceptional.wrappers.BaseExceptionRequestBody;
import com.attilapalf.exceptional.wrappers.ExceptionRefreshResponse;
import com.attilapalf.exceptional.wrappers.ExceptionSentResponse;
import com.attilapalf.exceptional.wrappers.ExceptionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Attila on 2015-06-13.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@RestController
public class ExceptionController {
    // TODO: if the user wants to refresh all his exceptions, we will need a handler for this

    @Autowired
    ExceptionLogic exceptionLogic;

    @RequestMapping(value = "/exception", method = RequestMethod.POST)
    public ResponseEntity<ExceptionSentResponse> sendException(@RequestBody ExceptionWrapper exceptionWrapper) {

        return new ResponseEntity<>(exceptionLogic.sendException(exceptionWrapper), HttpStatus.OK);
    }

    @RequestMapping(value = "/exception/refresh", method = RequestMethod.POST)
    public ResponseEntity<ExceptionRefreshResponse> refreshExceptions(@RequestBody BaseExceptionRequestBody requestBody) {

        return new ResponseEntity<>(exceptionLogic.refreshExceptions(requestBody), HttpStatus.OK);
    }
}
