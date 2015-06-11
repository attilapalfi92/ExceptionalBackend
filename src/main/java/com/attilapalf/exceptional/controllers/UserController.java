package com.attilapalf.exceptional.controllers;

import com.attilapalf.exceptional.businessLogic.UserBusinessLogic;
import com.attilapalf.exceptional.entities.UsersEntity;
import com.attilapalf.exceptional.repositories.UserCrud;
import com.attilapalf.exceptional.wrappers.RegistrationRequestBody;
import com.attilapalf.exceptional.wrappers.RegistrationResponseBody;
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
public class UserController {

    @Autowired
    private UserCrud userCrud;

    @Autowired
    private UserBusinessLogic userBusinessLogic;

    @RequestMapping(value = "/user/byPage/{page}/{size}", method = RequestMethod.GET)
    public ResponseEntity<Page<UsersEntity>> getAll(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(userCrud.findAll(new PageRequest(page, size)), HttpStatus.OK);
    }


    @RequestMapping(value = "/user/byId/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UsersEntity> getById(@PathVariable long userId) {
        return new ResponseEntity<>(userCrud.findOne(userId), HttpStatus.OK);
    }


    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<RegistrationResponseBody> registerUser(@RequestBody RegistrationRequestBody requestBody) {
        return new ResponseEntity<>(userBusinessLogic.registerUser(requestBody), HttpStatus.OK);
    }

}
