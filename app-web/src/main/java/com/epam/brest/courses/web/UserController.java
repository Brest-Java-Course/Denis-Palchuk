package com.epam.brest.courses.web;

import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by denis on 11/10/14.
 */
@Controller
@RequestMapping("/mvc")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/"})
    public ModelAndView launchInputForm() {
        ModelAndView view= new ModelAndView("inputForm","user",new User());
        return view;
    }

    @RequestMapping(value = {"/submitdata"})
    public ModelAndView getInputForm(@RequestParam("name")String userName,@RequestParam("login")String userLogin) {
        User user=new User();
        user.setUserName(userName);
        user.setLogin(userLogin);
        Long id=userService.addUser(user);
        user.setUserId(id);
        ModelAndView view= new ModelAndView("displayResult","user",user);
        return view;
    }
}
