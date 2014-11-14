package com.epam.brest.courses.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.Assert.assertEquals;
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
    public void version() {
        mockRestServiceServer.expect(requestTo(HOST + "/version"))
                             .andExpect(method(HttpMethod.GET))
                             .andRespond(withSuccess("123", MediaType.APPLICATION_JSON));
        String version=restClient.getRestVesrsion();
        assertEquals("123",version);
    }
}
