package com.example.movierestapi.controllers;

import com.example.restapimongodb.CustomizedResponse;
import com.example.restapimongodb.models.UserModel;
import com.example.restapimongodb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;

@Controller
public class UsersController
{
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity getUsers()
    {
        var customizedResponse = new CustomizedResponse(" List of all the users " , userService.getUsers());
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getAUsers(@PathVariable("id") String id)
    {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse(" User with id : " + id , Collections.singletonList(userService.getAUser(id)));
        } catch (Exception e) {
            customizedResponse=new CustomizedResponse(e.getMessage(), null);

            return new ResponseEntity(customizedResponse,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/users", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })

    public ResponseEntity createUsers(@RequestBody UserModel user)
    {
        var customizedResponse = new CustomizedResponse(" User created! : "  , Collections.singletonList(userService.addUser(user)));
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

}
