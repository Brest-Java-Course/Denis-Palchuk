package com.denispalchuk.epam.task;

import com.denispalchuk.epam.task.dao.UserDao;
import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by denis on 11/16/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-dao-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class UserDaoImplTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void getAllUsersTest() {
        List<User> users=userDao.getAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void addUserTest() {
        User user=new User(null,"userLogin4","userName4",4);
        userDao.addUser(user);
        List<User> users=userDao.getAllUsers();
        assertEquals(4,users.size());
    }

    @Test
    public void getAllMessageFromUserTest() {
        List<Message>messages=userDao.getAllMessageFromUser(1L);
        assertEquals(2,messages.size());
    }

    @Test
    public void getAverageAgeUsersWhoMessagedWithUserTest() {
        Integer integer=userDao.getAverageAgeUsersWhoMessagedWithUser(1L);
        assertEquals(19,integer.intValue());
    }

    @Test
    public void getUserByIdTest() {
        User user=userDao.getUserById(1L);
        assertEquals(user.getUserId(), new Long(1L));
    }

    @Test
    public void getUserByLoginTest() {
        User user=userDao.getUserByLogin("userLogin1");
        assertEquals(user.getUserLogin(),"userLogin1");
    }

    @Test
    public void removeUserTest() {
        userDao.removeUser(1L);
        List <User> users = userDao.getAllUsers();
        assertEquals(2L,users.size());
    }

    @Test
    public void updateUserTest() {
        User user=new User(1L,"newUserLogin1","newUserName1",1);
        userDao.updateUser(user);
        User newUser=userDao.getUserById(1L);
        assertEquals(user,newUser);
    }
}
