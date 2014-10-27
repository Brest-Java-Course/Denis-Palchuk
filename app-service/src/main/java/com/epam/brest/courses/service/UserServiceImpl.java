package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by denis on 10/25/14.
 */
public class UserServiceImpl implements UserService {
    public static final String ADMIN = "admin";
    private UserDao userDao;
    private static final Logger LOGGER = LogManager.getLogger();

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(), "User login should be specified.");
        Assert.notNull(user.getUserName(), "User name should be specified.");
        User existingUser = getUserByLogin(user.getLogin());
        if (existingUser != null) {
            LOGGER.warn("User is present in DB");
            throw new IllegalArgumentException("User is present in DB");
        }
        userDao.addUser(user);
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin({})",login);
        User user = null;
        Assert.notNull(login,"User login should be specified.");
        try {
            user = userDao.getUserByLogin(login);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("User with login '{}' doesn't exist!", login);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userDao.getUsers();
        Assert.notEmpty(users, "Empty DB");
        return users;
    }

    @Override
    public void deleteUser(Long id) {
        Assert.notNull(id, "User id can't be null");
        try {
            User user = userDao.getUserById(id);
            if (user.getLogin().equals(ADMIN))
                throw new IllegalArgumentException("Admin user can't be deleted");
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("User with id '{}' doesn't exist!", id);
            throw new IllegalArgumentException("User with this id doesn't exist!");
        }

        userDao.removeUser(id);
    }

    @Override
    public void updateUser(User user) {
        Assert.notNull(user);
        Assert.notNull(user.getUserId());
        Assert.notNull(user.getLogin(), "User login should be specified.");
        if (user.getLogin().equals(ADMIN))
            throw new IllegalArgumentException("User can't use 'admin' as login");
        Assert.notNull(user.getUserName(), "User name should be specified.");
        try {
            User existingUser = userDao.getUserById(user.getUserId());
            if (existingUser.getUserName().equals(ADMIN))
                throw new IllegalArgumentException("Admin's name can't be changed");
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("User with this id doesn't exist");
        }
        //TODO: checking the existence of login in the database
        userDao.updateUser(user);
    }

}
