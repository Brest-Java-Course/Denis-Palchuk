package com.denispalchuk.epam.task.service;

import com.denispalchuk.epam.task.dao.UserDao;
import com.denispalchuk.epam.task.domain.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        User user = new User(null, "NewLogin", "NewName", 10);

        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(null);

        Long userId=userDao.addUser(user);
        expectLastCall().andReturn(3L);

        replay(userDao);
        userService.addUser(user);

        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddUserWithSameLoginTest() {
        User user=new User(null, "NewLogin", "NewName", 10);

        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(user);

        replay(userDao);
        userService.addUser(user);

        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddUserWithBadAge() {
        User user=new User(null, "NewLogin", "NewName", -2);

        userDao.getUserByLogin(user.getUserLogin());
        expectLastCall().andReturn(null);

        replay(userDao);
        userService.addUser(user);

        verify(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddUserWithNullLogin() {
        User user=new User(null, null, "NewName", 10);
        replay(userDao);
        userService.addUser(user);
        verify(userDao);
    }


}
