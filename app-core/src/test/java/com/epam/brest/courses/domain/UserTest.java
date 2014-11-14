package com.epam.brest.courses.domain;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest extends TestCase {

    User user;
    @Before
    public void setUp() throws Exception {
        user = new User();
    }
    @Test
    public void testGetLogin() throws Exception {
        user.setUserName("User Name");
        Assert.assertEquals("User Name",user.getUserName());
    }
}