package com.denispalchuk.epam.task.rest.client;

import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by denis on 12/5/14.
 */
public class UserDaoRestImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger();
    RestTemplate restTemplate = new RestTemplate();
    private static final String REST_URL="http://localhost:8080/users/";

    @Override
    public List<User> getAllUsers() {
        LOGGER.debug("client request get all users");
        List<User> users=restTemplate.getForObject(REST_URL,List.class);
        return users;
    }

    @Override
    public User getUserById(Long userId) {
        LOGGER.debug("client request user by id {}",userId);
        User user=restTemplate.getForObject(REST_URL+"id/"+userId,User.class);
        return user;
    }

    @Override
    public User getUserByLogin(String userLogin) {
        LOGGER.debug("client request user by login {}",userLogin);
        User user=restTemplate.getForObject(REST_URL+"login/"+userLogin,User.class);
        return user;
    }

    @Override
    public Long addUser(User user) {
        Long id = restTemplate.postForObject(REST_URL, user, Long.class);
        LOGGER.debug("client request to add user {} and return id {}",user,id);
        return id;
    }

    @Override
    public void removeUser(Long userId) {
        LOGGER.debug("client request to delete user with id {}",userId);
        restTemplate.delete(REST_URL+"id/"+userId);
    }

    @Override
    public void updateUser(User user) {
        LOGGER.debug("client request to update user with id {}",user.getUserId());
        restTemplate.put(REST_URL,User.class);
    }

    @Override
    public Integer getAverageAgeUsersWhoMessagedWithUser(Long userId) {
        return null;
    }

    @Override
    public List<Message> getAllMessageFromUser(Long userId) {
        return null;
    }
}
