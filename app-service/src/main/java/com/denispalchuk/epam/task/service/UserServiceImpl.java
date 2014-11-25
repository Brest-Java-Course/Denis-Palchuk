package com.denispalchuk.epam.task.service;

import com.denispalchuk.epam.task.dao.UserDao;
import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

/**
 *
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao=userDao;
    }
    private static final Logger LOGGER= LogManager.getLogger();

    @Override
    public List<User> getAllUsers() {
        List <User> users = userDao.getAllUsers();
        Assert.notEmpty(users, "Empty list of Users");
        return users;
    }

    @Override
    public User getUserById(Long userId) {
        Assert.notNull(userId,"userId can't be null");
        User user=new User();
        try {
            user=userDao.getUserById(userId);
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.error("User with userId '{}' doesn't exist",userId);
            throw new IllegalArgumentException("User with this id doesn't not exist");
        }
        return user;
    }

    @Override
    public User getUserByLogin(String userLogin) {
        Assert.notNull(userLogin,"userLogin");
        User user=new User();
        try {
            user=userDao.getUserByLogin(userLogin);
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.error("User with userLogin '{}' doesn't exist",userLogin);
        }
        return user;
    }

    @Override
    public Long addUser(User user) {
        Assert.isNull(user.getUserId(), "userId should be null");

        Assert.notNull(user.getUserLogin(),"userLogin can't be null");
        if (user.getUserLogin().equals("admin")) {
            LOGGER.error("user login can't be 'admin' ");
            throw new IllegalArgumentException("user login can't be 'admin' ");
        }
        User existingUser = getUserByLogin(user.getUserLogin());
        if (existingUser != null) {
            LOGGER.error("user with userLogin '{}' already exist",user.getUserLogin());
            throw new IllegalArgumentException("user is already present in DB");
        }

        Assert.notNull(user.getUserName(),"userName can't be null");

        Assert.notNull(user.getUserAge(),"userAge can't be null");
        if (user.getUserAge() <= 0) {
            LOGGER.error("wrong user's age");
            throw new IllegalArgumentException("wrong user's age");
        }

        return userDao.addUser(user);
    }

    @Override
    public void removeUser(Long userId) {
        Assert.notNull(userId);
        User existingUser=getUserById(userId);
        if (existingUser == null) {
            LOGGER.error("user with id '{}' doesn't exist",userId);
            throw new IllegalArgumentException("user with this id doesn't exist");
        }
        if (existingUser.getUserLogin().equals("admin")) {
            LOGGER.error("can't delete admin's profile");
            throw new IllegalArgumentException("can't delete admin's profile");
        }

        userDao.removeUser(userId);
    }

    @Override
    public void updateUser(User user) {
        Assert.notNull(user.getUserId(),"userId can't be null");

        try {
            User existingUser = getUserById(user.getUserId());
            if (existingUser.getUserLogin().equals("admin")) {
                LOGGER.error("admin's login can't be changed");
                throw new IllegalArgumentException("admin's login can't be changed");
            }
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.error("user with this id doesn't exist");
            throw new IllegalArgumentException("user with this id doesn't exist");
        }

        Assert.notNull(user.getUserLogin(),"userLogin can't be null");

        User existingUser = getUserByLogin(user.getUserLogin());
        if (existingUser != null) {
            LOGGER.error("user with this login is already exist");
            throw new IllegalArgumentException("user with this login is already exist");
        }
        if (user.getUserLogin().equals("admin")) {
            LOGGER.error("login can't be changed to 'admin' ");
            throw new IllegalArgumentException("login can't be changed to 'admin' ");
        }

        Assert.notNull(user.getUserAge(),"userAge can't be null");
        if (user.getUserAge() <= 0 ) {
            LOGGER.error("wrong user's age {}",user.getUserAge());
            throw new IllegalArgumentException("wrong user's age");
        }

        Assert.notNull(user.getUserName(),"userName can't be null");
        userDao.updateUser(user);
    }

    @Override
    public Integer getAverageAgeUsersWhoMessagedWithUser(Long userId) {
        Assert.notNull(userId,"userId can't be null");
        User user=new User();
        try {
            user=userDao.getUserById(userId);
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.error("User with userId '{}' doesn't exist",userId);
        }
        return userDao.getAverageAgeUsersWhoMessagedWithUser(userId);
    }

    @Override
    public List<Message> getAllMessageFromUser(Long userId) {
        Assert.notNull(userId,"userId can't be null");
        User user=new User();
        try {
            user=userDao.getUserById(userId);
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.error("User with userId '{}' doesn't exist",userId);
        }
        return userDao.getAllMessageFromUser(userId);
    }
}
