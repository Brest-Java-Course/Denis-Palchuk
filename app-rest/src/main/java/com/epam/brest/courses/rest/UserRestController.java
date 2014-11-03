package com.epam.brest.courses.rest;

import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        List users = userService.getAllUsers();
        return new ResponseEntity(users, HttpStatus.OK);
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserById(@RequestBody Long id) {
        try  {
            User user=userService.getUserById(id);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity("User not found for id="+id+" error: "+ex.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
    @RequestMapping(value = "/login/{login}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserById(@RequestBody String login) {
        try  {
            User user=userService.getUserByLogin(login);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity("User not found for login="+login+" error: "+ex.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Long> addUser(@RequestBody User user) {
        Long id= userService.addUser(user);
        return new ResponseEntity(id, HttpStatus.CREATED);
    }


}
