package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;

import java.util.List;

/**
 * Created by denis on 10/25/14.
 */
public interface UserService {
    public Long addUser(User user);
    public User getUserById(Long id);
    public User getUserByLogin(String login);

    public void deleteUser(Long id);

    public List<User> getAllUsers();

    void updateUser(User user);
}
