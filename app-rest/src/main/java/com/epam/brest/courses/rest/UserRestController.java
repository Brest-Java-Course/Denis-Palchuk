package com.epam.brest.courses.rest;

import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Berdahuk.
 */
@Controller
@RequestMapping("/users")
public class UserRestController {


    @Resource
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity("User not found for id=" + id + " error:"
                    + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        List users = userService.getAllUsers();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity("", HttpStatus.OK);
    }

    @RequestMapping(value = "/login/{login}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable String login) {
        try {
            User user = userService.getUserByLogin(login);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity("User not found for login=" + login + " error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removeUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity("", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Long> addUser(@RequestBody User user) {
        Long id = userService.addUser(user);
        return new ResponseEntity(id, HttpStatus.CREATED);
    }


}
