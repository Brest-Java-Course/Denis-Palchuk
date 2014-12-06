package com.denispalchuk.epam.task.rest.client;

import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;

import java.util.List;

/**
 * Created by denis on 11/15/14.
 */
public interface UserDao {
    public List<User> getAllUsers();

    public User getUserById(Long userId);

    public User getUserByLogin(String userLogin);

    public Long addUser(User user);

    public void removeUser(Long userId);

    public void updateUser(User user);

    public Integer getAverageAgeUsersWhoMessagedWithUser(Long userId);

    public List<Message> getAllMessageFromUser(Long userId);
}
