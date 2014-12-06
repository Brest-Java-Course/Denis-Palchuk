package com.denispalchuk.epam.task.service;

import com.denispalchuk.epam.task.dao.UserDao;
import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * Created by denis on 11/24/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-service-mock-test.xml"})
public class UserServiceMockTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @After
    public void clean() {
        reset(userDao);
    }


    @Test
    public void AddUserTest() {
        User user = new User(null, "NewLogin", "NewName", 10, null);

        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(null);

        Long userId=userDao.addUser(user);
        expectLastCall().andReturn(3L);

        replay(userDao);
        userService.addUser(user);

        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddUserWithNotNullIdTest() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);
        userService.addUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddUserWithNullLogin() {
        User user=new User(null, null, "NewName", 10, null);
        userService.addUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddUserWithAdminlLogin() {
        User user=new User(null, "admin", "NewName", 10, null);
        userService.addUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddUserWithExistingLoginTest() {
        User user=new User(null, "NewLogin", "NewName", 10, null);

        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(user);

        replay(userDao);
        userService.addUser(user);

        verify(userDao);
    }
    @Test(expected = IllegalArgumentException.class)
    public void AddUserWithNullNameTest() {
        User user=new User(null, "NewLogin", null, 10, null);

        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(null);

        replay(userDao);
        userService.addUser(user);

        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddUserWithNullAge() {
        User user=new User(null, "NewLogin", "NewName", null, null);

        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(null);

        replay(userDao);
        userService.addUser(user);

        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddUserWithNegativeAge() {
        User user=new User(null, "NewLogin", "NewName", -2, null);

        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(null);

        replay(userDao);
        userService.addUser(user);

        verify(userDao);
    }

    @Test()
    public void getAllUsersTest() {
        List<User> users=new ArrayList<User>();
        users.add(new User());
        userDao.getAllUsers();
        expectLastCall().andReturn(users);
        replay(userDao);
        userService.getAllUsers();
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllUsersWithoutUsersTest() {
        userDao.getAllUsers();
        expectLastCall().andReturn(null);
        replay(userDao);
        userService.getAllUsers();
        verify(userDao);
    }

    @Test
    public void getUserByIdTest() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);

        userDao.getUserById(1L);
        expectLastCall().andReturn(user);

        replay(userDao);
        userService.getUserById(1L);
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserByIdEquallNulltest() {
        userService.getUserById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserByUnexistingIdTest() {
        userDao.getUserById(4L);
        expectLastCall().andThrow(new EmptyResultDataAccessException(0));
        replay(userDao);
        userService.getUserById(4L);
        verify(userDao);
    }

    @Test
    public void getUserByLoginTest() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);

        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(user);
        replay(userDao);
        userService.getUserByLogin(user.getUserLogin());
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserByNullLogin() {
        userService.getUserByLogin(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserByUnexistingLogin() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);

        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andThrow(new EmptyResultDataAccessException(0));
        replay(userDao);
        userService.getUserByLogin(null);
        verify(userDao);
    }

    @Test
    public void removeUserTest() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);

        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(user);

        userDao.removeUser(user.getUserId());
        expectLastCall();

        replay(userDao);
        userService.removeUser(user.getUserId());
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserWithNullIdTest() {
        userService.removeUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserWithUnexistingIdTest() {
        User user=new User(1L, "admin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andThrow(new EmptyResultDataAccessException(0));

        replay(userDao);
        userService.removeUser(user.getUserId());
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserWithLoginAdminTest() {
        User user=new User(1L, "admin", "NewName", 10, null);

        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(user);

        replay(userDao);
        userService.removeUser(user.getUserId());
        verify(userDao);
    }

    @Test
    public void updateUserTest() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(user);

        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(null);

        userDao.updateUser(user);
        expectLastCall();

        replay(userDao);
        userService.updateUser(user);
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithNullIdTest() {
        User user=new User(null, "NewLogin", "NewName", 10, null);
        userService.updateUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithUnexistingIdTest() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andThrow(new EmptyResultDataAccessException(0));

        replay(userDao);
        userService.updateUser(user);
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithAdminLoginTest() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);
        User existingUser = new User(1L, "admin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(existingUser);
        replay(userDao);
        userService.updateUser(user);
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithNullLoginTest() {
        User user=new User(1L, null, "NewName", 10, null);
        User existingUser = new User(1L, "admin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(existingUser);

        replay(userDao);
        userService.updateUser(user);
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithExistingLoginTest() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);
        User existingUser = new User(1L, "NewLogin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(existingUser);
        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(existingUser);
        replay(userDao);
        userService.updateUser(user);
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithNewAdminLoginTest() {
        User user=new User(1L, "admin", "NewName", 10, null);
        User existingUser = new User(1L, "NewLogin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(existingUser);
        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(null);
        replay(userDao);
        userService.updateUser(user);
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithNullAgeTest() {
        User user=new User(1L, "NewLogin", "NewName", null, null);
        User existingUser = new User(1L, "NewLogin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(existingUser);
        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(null);
        replay(userDao);
        userService.updateUser(user);
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithNegativeAgeTest() {
        User user=new User(1L, "NewLogin", "NewName", 0, null);
        User existingUser = new User(1L, "NewLogin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(existingUser);
        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(null);
        replay(userDao);
        userService.updateUser(user);
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithNullNameTest() {
        User user=new User(1L, "NewLogin", null, 10, null);
        User existingUser = new User(1L, "NewLogin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(existingUser);
        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(null);
        replay(userDao);
        userService.updateUser(user);
        verify(userDao);
    }

    @Test
    public void getAverageAgeUsersWhoMessagedWithUserTest() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(user);
        userDao.getAverageAgeUsersWhoMessagedWithUser(user.getUserId());
        expectLastCall().andReturn(10);

        replay(userDao);
        userService.getAverageAgeUsersWhoMessagedWithUser(user.getUserId());
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAverageAgeUsersWhoMessagedWithUserWithNullIdTest() {
        userService.getAverageAgeUsersWhoMessagedWithUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAverageAgeUsersWhoMessagedWithUserWithUnexistingIdTest() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andThrow(new EmptyResultDataAccessException(0));

        replay(userDao);
        userService.getAverageAgeUsersWhoMessagedWithUser(user.getUserId());
        verify(userDao);
    }

    @Test
    public void getAllMessageFromUserTest() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);
        List<Message> messages=new ArrayList<Message>();
        messages.add(new Message());
        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(user);

        userDao.getAllMessageFromUser(user.getUserId());
        expectLastCall().andReturn(messages);

        replay(userDao);
        userService.getAllMessageFromUser(user.getUserId());
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllMessageFromUserWithNullIdTest() {
        userService.getAllMessageFromUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllMessageFromUserWithUnexistingIdTest() {
        User user=new User(1L, "NewLogin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andThrow(new EmptyResultDataAccessException(0));

        replay(userDao);
        userService.getAllMessageFromUser(user.getUserId());
        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllMessageFromUserWithoutMessagesTest() {
        List <Message> messages=new ArrayList<Message>();
        User user=new User(1L, "NewLogin", "NewName", 10, null);
        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(user);

        userDao.getAllMessageFromUser(user.getUserId());
        expectLastCall().andThrow(new EmptyResultDataAccessException(0));

        replay(userDao);
        userService.getAllMessageFromUser(user.getUserId());
        verify(userDao);
    }




}
