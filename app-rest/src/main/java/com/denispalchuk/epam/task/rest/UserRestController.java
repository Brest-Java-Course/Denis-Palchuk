package com.denispalchuk.epam.task.rest;

import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;
import com.denispalchuk.epam.task.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by denis on 11/26/14.
 */
@Controller
@RequestMapping("/users")
public class UserRestController {
    @Resource
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
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
    @RequestMapping(value = "id/{id}/messages", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getAllMessageFromUser(@PathVariable Long userId) {
        List messages = userService.getAllMessageFromUser(userId);
        return new ResponseEntity(messages, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/id/{id}/avAge", method = RequestMethod.GET)
    public ResponseEntity<Integer> getAverageAgeUsersWhoMessagedWithUser(@PathVariable Long userId) {
        Integer averageAge = userService.getAverageAgeUsersWhoMessagedWithUser(userId);
        return new ResponseEntity(averageAge, HttpStatus.OK);
    }



    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity("", HttpStatus.OK);
    }

    @RequestMapping(value = "/login/{login}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) {
        try {
            User user = userService.getUserByLogin(login);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity("User not found for login=" + login + " error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        return new ResponseEntity("", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Long> addUser(@RequestBody User user) {
        Long id = userService.addUser(user);
        return new ResponseEntity(id, HttpStatus.CREATED);
    }
}
