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

    public List<User> getAllUsers() {
        LOGGER.debug("client request get all users");
        List<User> users=restTemplate.getForObject(REST_URL,List.class);
        return users;
    }

    public User getUserById(Long userId) {
        LOGGER.debug("client request user by id {}",userId);
        User user=restTemplate.getForObject(REST_URL+"id/{id}",User.class,userId);
        return user;
    }

    public User getUserByLogin(String userLogin) {
        LOGGER.debug("client request user by login {}",userLogin);
        User user=restTemplate.getForObject(REST_URL+"login/{userLogin}",User.class,userLogin);
        return user;
    }

    public Long addUser(User user) {
        Long id = restTemplate.postForObject(REST_URL, user, Long.class);
        LOGGER.debug("client request to add user {} and return id {}",user,id);
        return id;
    }

    public void removeUser(Long userId) {
        LOGGER.debug("client request to delete user with id {}",userId);
        restTemplate.delete(REST_URL+"id/{userId}",userId);
    }

    public void updateUser(User user) {
        LOGGER.debug("client request to update user with id {}",user.getUserId());
        restTemplate.put(REST_URL,User.class);
    }

    public Integer getAverageAgeUsersWhoMessagedWithUser(Long userId) {
        LOGGER.debug("client request to get average aga users who messaged with user with userid = {}",userId);
        return restTemplate.getForObject(REST_URL+"avage?userId={userId}",Integer.class,userId);
    }

    public List<Message> getAllMessageFromUser(Long userId) {
        LOGGER.debug("client request to get all message from user with userid = {}",userId);
        List<Message> messages=restTemplate.getForObject(REST_URL+"messages/?userId={userId}",List.class,userId);
        return messages;
    }
}
