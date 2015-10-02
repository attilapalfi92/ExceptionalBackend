package com.attilapalf.exceptional.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.attilapalf.exceptional.messages.BaseExceptionRequest;
import com.attilapalf.exceptional.messages.ExceptionInstanceWrapper;
import com.attilapalf.exceptional.messages.ExceptionRefreshResponse;
import com.attilapalf.exceptional.messages.ExceptionSentResponse;
import com.attilapalf.exceptional.services.ExceptionService;

/**
 * Created by Attila on 2015-06-13.
 */
@SuppressWarnings( "SpringJavaAutowiringInspection" )
@RestController
public class ExceptionController {
    @Autowired
    private ExceptionService exceptionService;

    @RequestMapping( value = "/exception", method = RequestMethod.POST )
    public ResponseEntity<ExceptionSentResponse> throwException( @RequestBody ExceptionInstanceWrapper exceptionInstanceWrapper ) {
        ExceptionSentResponse response = exceptionService.throwException( exceptionInstanceWrapper );
        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/exception/refresh", method = RequestMethod.POST )
    public ResponseEntity<ExceptionRefreshResponse> refreshExceptions( @RequestBody BaseExceptionRequest requestBody ) {
        return new ResponseEntity<>( exceptionService.refreshExceptions( requestBody ), HttpStatus.OK );
    }
}
