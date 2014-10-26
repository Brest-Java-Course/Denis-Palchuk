package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/testServiceApplicationContextSpring.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class UserServiceImplTest {
    public static final String ADMIN = "admin";
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullUser() throws Exception {
        userService.addUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyUser() throws Exception {
        userService.addUser(new User());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNotNullIdUser() throws Exception {
        userService.addUser(new User(12L, "", ""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithSameLogin() throws Exception {
        userService.addUser(new User(null, ADMIN, ADMIN));
        userService.addUser(new User(null, ADMIN, ADMIN));
    }

    @Test
    public void testAddUser() throws Exception {
        userService.addUser(new User(null, ADMIN, ADMIN));
        User user = userService.getUserByLogin(ADMIN);
        Assert.assertEquals(ADMIN, user.getLogin());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserByNullLogin() {
        userService.getUserByLogin(null);
    }

    @Test
    public void testGetUserByLogin() {
        userService.addUser(new User(null, "NewLogin", "NewName"));
        User user = userService.getUserByLogin("NewLogin");
        Assert.assertNotNull(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserWithNonexistentId() throws Exception {
        userService.deleteUser(10L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserWithNullId() throws Exception {
        userService.deleteUser(null);
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUser(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void testGetAllUsersWithEmptyTable() {
        userService.deleteUser(1L);
        userService.deleteUser(2L);
        userService.getAllUsers();
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userService.getAllUsers();
        Assert.assertNotNull(users);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserWithAdminLogin() {
        User user = new User(1L, ADMIN, ADMIN);
        userService.updateUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void testUpdateAdminLoginToNonAdmin() {
        userService.addUser(new User(null, ADMIN, ADMIN));
        User user = userService.getUserByLogin(ADMIN);
        user.setUserName("NotAdmin");
        userService.updateUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateWithEmptyFields() {
        userService.updateUser(new User(1L, null, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNullIdUser() {
        userService.updateUser(new User(null, "NewLogin", "NewName"));
    }

    @Test
    public void testUpdateUser() {
        userService.updateUser(new User(1L, "Login1", "Name1"));
        User user = userService.getUserByLogin("Login1");
        Assert.assertEquals("Name1", user.getUserName());
    }
}