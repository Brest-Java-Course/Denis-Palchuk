package com.epam.brest.courses.dao;

import java.util.List;
import com.epam.brest.courses.domain.User;
/**
 * Created by denis on 10/20/14.
 */
public interface UserDao {
    public List<User> getUsers();
    public User getUserById(Long id);
    public User getUserByLogin(String login);
    public void addUser(User user);
    public void removeUser(Long userId);
    public void updateUser (User user);
}
