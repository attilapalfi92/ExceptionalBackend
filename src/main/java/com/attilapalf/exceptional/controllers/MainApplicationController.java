package com.attilapalf.exceptional.controllers;

import com.attilapalf.exceptional.services.MainApplicationService;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.users.UserCrud;
import com.attilapalf.exceptional.wrappers.AppStartRequestBody;
import com.attilapalf.exceptional.wrappers.AppStartResponseBody;
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
@SuppressWarnings("SpringJavaAutowiringInspection")
@RestController
public class MainApplicationController {

    @Autowired
    private UserCrud userCrud;

    @Autowired
    private MainApplicationService mainApplicationService;


    @RequestMapping(value = "/user/byPage/{page}/{size}", method = RequestMethod.GET)
    public ResponseEntity<Page<UsersEntity>> getAll(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(userCrud.findAll(new PageRequest(page, size)), HttpStatus.OK);
    }


    @RequestMapping(value = "/user/byId/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UsersEntity> getById(@PathVariable BigInteger userId) {
        return new ResponseEntity<>(userCrud.findOne(userId), HttpStatus.OK);
    }


    @RequestMapping(value = "/application/firstAppStart", method = RequestMethod.POST)
    public ResponseEntity<AppStartResponseBody> firstAppStart(@RequestBody AppStartRequestBody requestBody) {
        try {
            AppStartResponseBody response = mainApplicationService.firstAppStart(requestBody);
            ResponseEntity<AppStartResponseBody> result = new ResponseEntity<>(response, HttpStatus.OK);
            return result;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/application/regularAppStart", method = RequestMethod.POST)
    public ResponseEntity<AppStartResponseBody> regularAppStart(@RequestBody AppStartRequestBody requestBody) {
        return new ResponseEntity<>(mainApplicationService.regularAppStart(requestBody), HttpStatus.OK);
    }

}
