package com.epam.brest.courses.client;

import com.epam.brest.courses.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by denis on 11/14/14.
 */
public class RestClientTest {
    static private final String HOST = "http://HOST";

    private RestClient restClient;

    private MockRestServiceServer mockRestServiceServer;

    @Before
    public void setUp() {
        restClient = new RestClient(HOST);
        mockRestServiceServer = MockRestServiceServer.createServer(restClient.getRestTemplate());
    }

    @After
    public void check() {
        mockRestServiceServer.verify();
    }

    @Test
    public void versionTest() {
        mockRestServiceServer.expect(requestTo(HOST + "/version"))
                             .andExpect(method(HttpMethod.GET))
                             .andRespond(withSuccess("123", MediaType.APPLICATION_JSON));
        String version=restClient.getRestVesrsion();
        assertEquals("123",version);
    }

    @Test
    public void addUserTest() {
        mockRestServiceServer.expect(requestTo(HOST+ "/users"))
                            .andExpect(method(HttpMethod.POST))
                            .andExpect(content().contentType("application/json;charset=UTF-8"))
                            .andExpect(content().string("{\"userId\":null,\"login\":\"testLogin\",\"userName\":\"testName\"}"))
                            .andRespond(withSuccess("4",MediaType.APPLICATION_JSON));

        User user = new User();
        user.setUserName("testName");
        user.setLogin("testLogin");
        Long id=restClient.addUser(user);
        assertEquals(new Long(4L),id);
    }

    @Test
    public void getUserById() {
        mockRestServiceServer.expect(requestTo(HOST+ "/users/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"userId\":1,\"login\":\"testLogin\",\"userName\":\"testName\"}",MediaType.APPLICATION_JSON));

        User user = new User();
        user.setUserName("testName");
        user.setLogin("testLogin");
        user.setUserId(1L);
        User newUser=restClient.getUserById(1L);
        //Правильнее сделать метод в юзере equals и сравнить объекты
        //Но слишком мало времени =(
        assertEquals(user.toString(),newUser.toString());
    }
}
