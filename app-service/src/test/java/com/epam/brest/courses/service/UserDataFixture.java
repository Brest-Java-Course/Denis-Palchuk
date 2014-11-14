package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;

/**
 * Created by denis on 10/27/14.
 */
public class UserDataFixture {

    public static User getNewUser() {
        User user = new User();
        user.setUserName("name");
        user.setLogin("login");
        return user;
    }

    public static User getExistUser(long id) {
        User user =new User();
        user.setUserName("name");
        user.setLogin("login");
        user.setUserId(id);
        return user;
    }
}
