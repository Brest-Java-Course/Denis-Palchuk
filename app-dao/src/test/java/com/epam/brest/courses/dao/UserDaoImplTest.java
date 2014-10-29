package com.epam.brest.courses.dao;
import com.epam.brest.courses.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import java.util.List;

/**
 * Created by denis on 10/22/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-dao-tests.xml"})
public class UserDaoImplTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void getUsers() {
        List<User> users=userDao.getUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }
    @Test
    public void getUserById() {
        User user=userDao.getUserById(1L);
        assertEquals(user.getUserId(),(Long)1L);
    }
    @Test
    public void getUserByLogin() {
        User user=userDao.getUserByLogin("userLogin1");
        assertEquals(user.getLogin(),"userLogin1");
    }
    @Test
    public void addUser() {
        List<User> users = userDao.getUsers();
        int sizeBefore = users.size();
        User user = new User();
        user.setUserId(null);
        user.setLogin("userLogin3");
        user.setUserName("userName3");
        userDao.addUser(user);
        users = userDao.getUsers();
        assertEquals(sizeBefore, users.size() - 1);
    }
//    TODO:debug test

    @Test
    public void removeUser() {
        List<User> users=userDao.getUsers();
        int sizeBefore=users.size();
        userDao.removeUser(2L);
        users=userDao.getUsers();
        assertEquals(sizeBefore,users.size()+1);
    }
    @Test
    public void updateUser() {
        User user = new User();
        user.setLogin("uniqLogin");
        user.setUserId(2L);
        user.setUserName("uniqName");
        userDao.updateUser(user);
        User updatedUser = userDao.getUserById(2L);
        assertEquals(user.getLogin(),updatedUser.getLogin());
        assertEquals(user.getUserName(),updatedUser.getUserName());
    }
}
