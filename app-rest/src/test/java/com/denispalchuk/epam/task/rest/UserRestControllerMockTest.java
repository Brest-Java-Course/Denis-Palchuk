package com.denispalchuk.epam.task.rest;

import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;
import com.denispalchuk.epam.task.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by denis on 11/26/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-rest-mock-test.xml"})
public class UserRestControllerMockTest {
    private MockMvc mockMvc;

    @Resource
    private UserRestController userRestController;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(userRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void clean() {
        reset(userService);
    }

    @Test
    public void addUserTest() throws Exception {
        userService.addUser(anyObject(User.class));
        expectLastCall().andReturn(1L);

        replay(userService);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(new User(null, "NewLogin", "NewName", 10));
        this.mockMvc.perform(
                post("/users")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
        verify(userService);
    }

    @Test
    public void getAllUsersTest() throws Exception {
        List<User> users = new ArrayList<User>();
        users.add(new User(1L, "NewLogin1", "NewName1", 10));
        users.add(new User(2L, "NewLogin2", "NewName2", 10));
        userService.getAllUsers();
        expectLastCall().andReturn(users);
        replay(userService);
        this.mockMvc.perform(
                get("/users").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"userId\":1,\"userLogin\":\"NewLogin1\",\"userName\":\"NewName1\",\"userAge\":10}," +
                        "{\"userId\":2,\"userLogin\":\"NewLogin2\",\"userName\":\"NewName2\",\"userAge\":10}]"));


    }

    @Test
    public void updateUserTest() throws Exception {
        User user=new User(1L,"NewLogin","NewName",20);
        userService.updateUser(user);
        expectLastCall();
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson=objectMapper.writeValueAsString(user);
        replay(userService);
        this.mockMvc.perform(put("/users/")
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userService);
    }


    @Test
    public void getUserByIdTest() throws Exception {
        userService.getUserById(1L);
        expectLastCall().andReturn(new User(1L, "NewLogin", "NewName", 10));
        replay(userService);
        this.mockMvc.perform(
                get("/users/id/1").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"userId\":1,\"userLogin\":\"NewLogin\",\"userName\":\"NewName\",\"userAge\":10}"));
        verify(userService);
    }

    @Test
    public void removeUserTest() throws Exception {
        userService.removeUser(1L);
        expectLastCall();

        replay(userService);
        this.mockMvc.perform(
                delete("/users/id/1").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
        verify(userService);
    }


    @Test
    public void getUserByLoginTest() throws Exception {
        userService.getUserByLogin("NewLogin");
        expectLastCall().andReturn(new User(1L, "NewLogin", "NewName", 10));
        replay(userService);
        this.mockMvc.perform(
                get("/users/login/NewLogin").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"userId\":1,\"userLogin\":\"NewLogin\",\"userName\":\"NewName\",\"userAge\":10}"));
        verify(userService);
    }

    @Test
    public void getAllMessageFromUserTest() throws Exception {
        List<Message> messages = new ArrayList<Message>();
        messages.add(new Message(1L,2L,3L,"Text1",new LocalDateTime(2014, 11, 23, 12, 0, 0, 0)));
        messages.add(new Message(2L,2L,3L,"Text2",new LocalDateTime(2014, 11, 23, 13, 0, 0, 0)));
        userService.getAllMessageFromUser(1L);
        expectLastCall().andReturn(messages);

        replay(userService);
        this.mockMvc.perform(get("/users/messages/").accept(MediaType.APPLICATION_JSON).param("userId","1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"messageId\":1,\"messageFromUserId\":2,\"messageToUserId\":3,\"messageText\":\"Text1\",\"messageDateTime\":\"2014-11-23 12:00:00\"}," +
                        "{\"messageId\":2,\"messageFromUserId\":2,\"messageToUserId\":3,\"messageText\":\"Text2\",\"messageDateTime\":\"2014-11-23 13:00:00\"}]"));
    }

    @Test
    public void getAverageAgeUsersWhoMessagedWithUserTest() throws Exception {
        userService.getAverageAgeUsersWhoMessagedWithUser(1L);
        expectLastCall().andReturn(20);
        replay(userService);
        this.mockMvc.perform(get("/users/avage/").accept(MediaType.APPLICATION_JSON).param("userId","1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("20"));
    }

}
