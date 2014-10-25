package com.epam.brest.courses.service;
import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Created by denis on 10/25/14.
 */
public class UserServiceImpl implements UserService {
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
            throw new IllegalArgumentException("User is present in DB");
        }
        userDao.addUser(user);
    }

    @Override
    public User getUserByLogin(String login) {
        User user = null;
        try {
            user = userDao.getUserByLogin(login);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("User with login {} doesn't exist!", login);
            return null;
        }
        return user;
    }
}
