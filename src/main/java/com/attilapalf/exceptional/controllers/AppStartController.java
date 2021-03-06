package com.attilapalf.exceptional.controllers;

import com.attilapalf.exceptional.services.AppStartService;
import com.attilapalf.exceptional.entities.User;
import com.attilapalf.exceptional.repositories.users.UserCrud;
import com.attilapalf.exceptional.messages.AppStartRequest;
import com.attilapalf.exceptional.messages.AppStartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 * Created by Attila on 2015-06-10.
 */
@RestController
public class AppStartController {

    @Autowired
    private UserCrud userCrud;

    @Autowired
    private AppStartService appStartService;


    @RequestMapping(value = "/user/byPage/{page}/{size}", method = RequestMethod.GET)
    public ResponseEntity<Page<User>> getAll(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(userCrud.findAll(new PageRequest(page, size)), HttpStatus.OK);
    }


    @RequestMapping(value = "/user/byId/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getById(@PathVariable BigInteger userId) {
        return new ResponseEntity<>(userCrud.findOne(userId), HttpStatus.OK);
    }


    @RequestMapping(value = "/application/firstAppStart", method = RequestMethod.POST)
    public ResponseEntity<AppStartResponse> firstAppStart(@RequestBody AppStartRequest requestBody) {
        try {
            AppStartResponse response = appStartService.firstAppStart(requestBody);
            ResponseEntity<AppStartResponse> result = new ResponseEntity<>(response, HttpStatus.OK);
            return result;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/application/regularAppStart", method = RequestMethod.POST)
    public ResponseEntity<AppStartResponse> regularAppStart(@RequestBody AppStartRequest requestBody) {
        return new ResponseEntity<>( appStartService.regularAppStart(requestBody), HttpStatus.OK);
    }

}
