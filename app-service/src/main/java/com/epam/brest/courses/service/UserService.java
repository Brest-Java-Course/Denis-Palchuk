package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;

/**
 * Created by denis on 10/25/14.
 */
public interface UserService {
    public void addUser(User user);
    public User getUserByLogin(String login);
}
