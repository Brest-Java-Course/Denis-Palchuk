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
    public static final String ERROR_USER_LOGIN_SHOULD_BE_SPECIFIED = "User login should be specified.";
    public static final String ERROR_USER_NAME_SHOULD_BE_SPECIFIED = "User name should be specified.";
    public static final String ERROR_USER_IS_PRESENT_IN_DB = "User is present in DB";
    public static final String USER_WITH_LOGIN_DOESN_T_EXIST = "User with login '{}' doesn't exist!";
    public static final String LOG_GET_USER_BY_LOGIN = "getUserByLogin({})";
    public static final String ERROR_EMPTY_DB = "Empty DB";
    public static final String ERROR_USER_ID_CANT_BE_NULL = "User id can't be null";
    public static final String ADMIN_USER_CANT_BE_DELETED = "Admin user can't be deleted";
    public static final String ERROR_USER_WITH_ID_DOESNT_EXIST = "User with id '{}' doesn't exist!";
    public static final String ERROR_USER_WITH_THIS_ID_DOESN_T_EXIST = "User with this id doesn't exist!";
    public static final String ERROR_USER_CANT_USE_ADMIN_AS_LOGIN = "User can't use 'admin' as login";
    public static final String ERROR_ADMINS_NAME_CANT_BE_CHANGED = "Admin's name can't be changed";
    private UserDao userDao;
    private static final Logger LOGGER = LogManager.getLogger();

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(), ERROR_USER_LOGIN_SHOULD_BE_SPECIFIED);
        Assert.notNull(user.getUserName(), ERROR_USER_NAME_SHOULD_BE_SPECIFIED);
        User existingUser = getUserByLogin(user.getLogin());
        if (existingUser != null) {
            LOGGER.warn(ERROR_USER_IS_PRESENT_IN_DB);
            throw new IllegalArgumentException(ERROR_USER_IS_PRESENT_IN_DB);
        }
        userDao.addUser(user);
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug(LOG_GET_USER_BY_LOGIN,login);
        User user = null;
        Assert.notNull(login,ERROR_USER_LOGIN_SHOULD_BE_SPECIFIED);
        try {
            user = userDao.getUserByLogin(login);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(USER_WITH_LOGIN_DOESN_T_EXIST, login);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userDao.getUsers();
        Assert.notEmpty(users, ERROR_EMPTY_DB);
        return users;
    }

    @Override
    public void deleteUser(Long id) {
        Assert.notNull(id, ERROR_USER_ID_CANT_BE_NULL);
        try {
            User user = userDao.getUserById(id);
            if (user.getLogin().equals(ADMIN))
                throw new IllegalArgumentException(ADMIN_USER_CANT_BE_DELETED);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(ERROR_USER_WITH_ID_DOESNT_EXIST, id);
            throw new IllegalArgumentException(ERROR_USER_WITH_THIS_ID_DOESN_T_EXIST);
        }

        userDao.removeUser(id);
    }

    @Override
    public void updateUser(User user) {
        Assert.notNull(user);
        Assert.notNull(user.getUserId());
        Assert.notNull(user.getLogin(), ERROR_USER_LOGIN_SHOULD_BE_SPECIFIED);
        if (user.getLogin().equals(ADMIN))
            throw new IllegalArgumentException(ERROR_USER_CANT_USE_ADMIN_AS_LOGIN);
        Assert.notNull(user.getUserName(), ERROR_USER_NAME_SHOULD_BE_SPECIFIED);
        try {
            User existingUser = userDao.getUserById(user.getUserId());
            if (existingUser.getUserName().equals(ADMIN))
                throw new IllegalArgumentException(ERROR_ADMINS_NAME_CANT_BE_CHANGED);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException(ERROR_USER_WITH_THIS_ID_DOESN_T_EXIST);
        }
        User existingUser = getUserByLogin(user.getLogin());
        if (existingUser != null) {
            LOGGER.warn(ERROR_USER_IS_PRESENT_IN_DB);
            throw new IllegalArgumentException(ERROR_USER_IS_PRESENT_IN_DB);
        }
        userDao.updateUser(user);
    }

}
