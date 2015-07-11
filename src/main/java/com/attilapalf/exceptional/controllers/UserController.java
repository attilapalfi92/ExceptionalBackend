package com.attilapalf.exceptional.controllers;

import com.attilapalf.exceptional.logic.UserLogic;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.UserCrud;
import com.attilapalf.exceptional.wrappers.AppStartRequestBody;
import com.attilapalf.exceptional.wrappers.AppStartResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Attila on 2015-06-10.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@RestController
public class UserController {

    @Autowired
    private UserCrud userCrud;

    @Autowired
    private UserLogic userLogic;

    // TODO: if the user wants to refresh all his friends, we will need a handler for this


    @RequestMapping(value = "/user/byPage/{page}/{size}", method = RequestMethod.GET)
    public ResponseEntity<Page<UsersEntity>> getAll(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(userCrud.findAll(new PageRequest(page, size)), HttpStatus.OK);
    }


    @RequestMapping(value = "/user/byId/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UsersEntity> getById(@PathVariable long userId) {
        return new ResponseEntity<>(userCrud.findOne(userId), HttpStatus.OK);
    }


    @RequestMapping(value = "/user/firstAppStart", method = RequestMethod.POST)
    public ResponseEntity<AppStartResponseBody> userFirstAppStart(@RequestBody AppStartRequestBody requestBody) {
        try {
            AppStartResponseBody response = userLogic.firstAppStart(requestBody);
            ResponseEntity<AppStartResponseBody> result = new ResponseEntity<>(response, HttpStatus.OK);
            return result;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/user/appStart", method = RequestMethod.POST)
    public ResponseEntity<AppStartResponseBody> userAppStart(@RequestBody AppStartRequestBody requestBody) {
        return new ResponseEntity<>(userLogic.appStart(requestBody), HttpStatus.OK);
    }

}
