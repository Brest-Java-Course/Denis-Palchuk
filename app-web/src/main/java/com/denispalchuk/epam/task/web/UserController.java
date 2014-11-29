package com.denispalchuk.epam.task.web;

import com.denispalchuk.epam.task.domain.User;
import com.denispalchuk.epam.task.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String init() {
        return "redirect:/inputForm";
    }

    @RequestMapping("/inputForm")
    public ModelAndView launchInputForm() {
      return new ModelAndView("inputForm", "user", new User());
    }

    @RequestMapping("/submitData")
    public String getInputForm(@RequestParam("Login")String userLogin, @RequestParam("Name")String userName,
                               @RequestParam("Age")Integer userAge) {
        User user = new User();
        user.setUserLogin(userLogin);
        user.setUserName(userName);
        user.setUserAge(userAge);
        userService.addUser(user);
        return "redirect:/usersList";
    }
    @RequestMapping("/deleteData")
    public String deleteUser(@RequestParam("userId")Long userId) {
        userService.removeUser(userId);
        return "redirect:/usersList";
    }
    @RequestMapping("/usersList")
    public ModelAndView getListUsersView() {
      List<User> users = userService.getAllUsers();
      LOGGER.debug("users.size = " + users.size());
      ModelAndView view = new ModelAndView("usersList", "users", users);
      return view;
    }

    @RequestMapping("/user/{id}")
    public ModelAndView launchUserForm(@PathVariable("id") Long userId) {
        User user=userService.getUserById(userId);
        LOGGER.debug("getUserById {}",userId);
        return new ModelAndView("userForm","user",user);
    }
}
