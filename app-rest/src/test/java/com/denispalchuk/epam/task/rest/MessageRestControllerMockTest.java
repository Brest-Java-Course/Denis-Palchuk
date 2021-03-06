package com.denispalchuk.epam.task.rest;

import com.denispalchuk.epam.task.domain.CustomDataDeserializer;
import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.LocalDateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.JodaDateTimeFormatAnnotationFormatterFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.easymock.EasyMock.*;
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
public class MessageRestControllerMockTest {
    private MockMvc mockMvc;

    @Resource
    private MessageRestController messageRestController;

    @Autowired
    private MessageService messageService;

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(messageRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void clean() {
        reset(messageService);
    }

    @Test
    public void getMessageByIdTest() throws Exception {
        Message message = new Message(1L, 2L, 3L, "NewText", new LocalDateTime(2014, 11, 23, 12, 0, 0, 0));
        messageService.getMessageById(1L);
        expectLastCall().andReturn(message);

        replay(messageService);
        this.mockMvc.perform(get("/messages/id/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"messageId\":1,\"messageFromUserId\":2,\"messageToUserId\":3,\"messageText\":\"NewText\",\"messageDateTime\":\"2014-11-23 12:00:00\"}"));
        verify(messageService);
    }

    @Test
    public void getMessagesTest() throws Exception {
        List<Message> messages = new ArrayList<Message>();
        messages.add(new Message(1L, 2L, 3L, "NewText", new LocalDateTime(2014, 11, 23, 12, 0, 0, 0)));
        messages.add(new Message(1L, 2L, 3L, "NewText", new LocalDateTime(2014, 11, 24, 12, 0, 0, 0)));
        messageService.getAllMessages();
        expectLastCall().andReturn(messages);

        replay(messageService);
        this.mockMvc.perform(get("/messages").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"messageId\":1,\"messageFromUserId\":2,\"messageToUserId\":3,\"messageText\":\"NewText\",\"messageDateTime\":\"2014-11-23 12:00:00\"}," +
                        "{\"messageId\":1,\"messageFromUserId\":2,\"messageToUserId\":3,\"messageText\":\"NewText\",\"messageDateTime\":\"2014-11-24 12:00:00\"}]"));
        verify(messageService);
    }

    @Test
    public void updateMessageTest() throws Exception {
        Message message = new Message(1L, 2L, 3L, "NewText", new LocalDateTime(2014, 11, 24, 12, 0, 0, 0));
        messageService.updateMessage(message);
        expectLastCall();
        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson = objectMapper.writeValueAsString(message);
        replay(messageService);
        this.mockMvc.perform(put("/messages/")
                .accept(MediaType.APPLICATION_JSON)
                .content(messageJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(messageService);
    }

    @Test
    public void addUserTest() throws Exception {
        messageService.addMessage(anyObject(Message.class));
        expectLastCall().andReturn(1L);

        replay(messageService);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(new Message(null, 2L, 3L, "NewText", null));
        this.mockMvc.perform(
                post("/messages")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
        verify(messageService);
    }

    @Test
    public void removeUserTest() throws Exception {
        messageService.removeMessage(1L);
        expectLastCall();

        replay(messageService);
        this.mockMvc.perform(
                delete("/messages/id/1").accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
        verify(messageService);
    }

    @Test
    public void getMessageByTimePeriodTest() throws Exception {
        List<Message> messages = new ArrayList<Message>();
        LocalDateTime startDateTime = new LocalDateTime(2014, 11, 24, 12, 0, 0, 0);
        LocalDateTime finishDateTime = new LocalDateTime(2014, 12, 24, 12, 0, 0, 0);
        messages.add(new Message(1L, 2L, 3L, "NewText", new LocalDateTime(2014, 11, 25, 12, 12, 30, 0)));
        messages.add(new Message(2L, 2L, 3L, "NewText", new LocalDateTime(2014, 11, 26, 12, 12, 30, 0)));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        Map<String,LocalDateTime> params= new HashMap<String,LocalDateTime>();
        params.put("startDateTime",startDateTime);
        params.put("finishDateTime",finishDateTime);
        String dateTimeJson=objectMapper.writeValueAsString(params);
        messageService.getAllMessagesByTimePeriod(startDateTime, finishDateTime);
        expectLastCall().andReturn(messages);
        replay(messageService);
        this.mockMvc.perform(post("/messages/bytime")
                .accept(MediaType.APPLICATION_JSON)
                .content(dateTimeJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"messageId\":1,\"messageFromUserId\":2,\"messageToUserId\":3,\"messageText\":\"NewText\",\"messageDateTime\":\"2014-11-25 12:12:30\"}," +
                        "{\"messageId\":2,\"messageFromUserId\":2,\"messageToUserId\":3,\"messageText\":\"NewText\",\"messageDateTime\":\"2014-11-26 12:12:30\"}]"));
    }
}
