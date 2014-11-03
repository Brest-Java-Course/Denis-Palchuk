package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertSame;

/**
 * Created by denis on 10/27/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-service-tests.xml"})
public class UserServiceImplMockTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @After
    public void  clean() {
        reset(userDao);
    }
    @Test
    public void addUser() {
        User user = UserDataFixture.getNewUser();
        // предполагаем, что этот методы будут вызваны (порядок не важен)
        userDao.getUserByLogin(user.getLogin());
        expectLastCall().andReturn(null);

        Long id=userDao.addUser(user);
        expectLastCall().andReturn(3L);

        // переключаем userDao в режим репликации
        replay(userDao);

        userService.addUser(user);

        //проверка все ли методы выполнились
        verify(userDao);
    }


    @Test(expected = IllegalArgumentException.class)
    public void addUserWithSameLogin() {
        User user = UserDataFixture.getNewUser();

        expect(userDao.getUserByLogin(user.getLogin())).andReturn(user);

        replay(userDao);

        userService.addUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserException() {
        User user= UserDataFixture.getNewUser();
        expect(userDao.getUserByLogin(user.getLogin())).andThrow(new IllegalArgumentException());
        replay(userDao);

        userService.addUser(user);
    }
    @Test
    public void getUserByLogin() {
        User user=UserDataFixture.getExistUser(1L);

        expect(userDao.getUserByLogin(user.getLogin())).andReturn(user);

        replay(userDao);

        User result = userService.getUserByLogin(user.getLogin());

        verify(userDao);

        assertSame(user,result);
    }
}
