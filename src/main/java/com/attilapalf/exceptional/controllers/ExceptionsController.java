package com.attilapalf.exceptional.controllers;

import com.attilapalf.exceptional.wrappers.ExceptionWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Attila on 2015-06-13.
 */
public class ExceptionsController {
    // TODO: if the user wants to refresh all his exceptions, we will need a handler for this

    @RequestMapping(value = "/exception", method = RequestMethod.PUT)
    public ResponseEntity<String> putNewException(@RequestBody ExceptionWrapper exceptionWrapper) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
