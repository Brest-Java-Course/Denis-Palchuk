package com.denispalchuk.epam.task.service;

import com.denispalchuk.epam.task.dao.MessageDao;
import com.denispalchuk.epam.task.domain.Message;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * Created by denis on 11/25/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-service-mock-test.xml"})
public class messageServiceMockTest {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MessageService messageService;

    @After
    public void clean() {
        reset(messageDao);
    }

    @Test
    public void addMessageTest() {
        Message message = new Message(null, 2L, 3L, "NewMessage", null);
        messageDao.addMessage(message);
        expectLastCall().andReturn(1L);

        replay(messageDao);
        messageService.addMessage(message);
        verify(messageDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMessageWithNotNullIdTest() {
        Message message = new Message(1L, 2L, 3L, "NewMessage", null);
        messageService.addMessage(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMessageWithNotNullDateTimeTest() {
        Message message = new Message(null, null, 3L, "NewMessage", new DateTime(2014, 11, 22, 12, 0, 0, 0));
        messageService.addMessage(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMessageWithNullFromUserIdTest() {
        Message message = new Message(null, null, 3L, "NewMessage", null);
        messageService.addMessage(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMessageWithNullToUserIdTest() {
        Message message = new Message(null, 2L, null, "NewMessage", null);
        messageService.addMessage(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMessageWithNullTextTest() {
        Message message = new Message(null, 2L, 3L, null, null);
        messageService.addMessage(message);
    }

    @Test
    public void getMessageByIdTest() {
        Message message = new Message(1L, 2L, 3L, "NewMessage", null);
        messageDao.getMessageById(message.getMessageId());
        expectLastCall().andReturn(message);
        replay(messageDao);
        messageService.getMessageById(message.getMessageId());
        verify(messageDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMessageByNullIdTest() {
        messageService.getMessageById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMessageByUnexistingIdTest() {
        messageDao.getMessageById(1L);
        expectLastCall().andThrow(new EmptyResultDataAccessException(0));

        replay(messageDao);
        messageService.getMessageById(1L);
        verify(messageDao);
    }

    @Test
    public void removeMessageTest() {
        Message message = new Message(1L, 2L, 3L, "NewMessage", null);
        messageDao.getMessageById(message.getMessageId());
        expectLastCall().andReturn(message);
        messageDao.removeMessage(message.getMessageId());
        expectLastCall();

        replay(messageDao);
        messageService.removeMessage(message.getMessageId());
        verify(messageDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeMessageWithNullIdTest() {
        messageService.removeMessage(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeMessageWithUnexistingIdTest() {
        messageDao.getMessageById(1L);
        expectLastCall().andThrow(new EmptyResultDataAccessException(0));

        replay(messageDao);
        messageService.getMessageById(1L);
        verify(messageDao);
    }

    @Test
    public void updateMessageTest() {
        Message message = new Message(1L, 2L, 3L, "NewMessage", new DateTime(2014, 11, 22, 12, 0, 0, 0));
        messageDao.updateMessage(message);
        expectLastCall();

        replay(messageDao);
        messageService.updateMessage(message);
        verify(messageDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateMessageWithNullIdTest() {
        Message message = new Message(null, 2L, 3L, "NewMessage", new DateTime(2014, 11, 22, 12, 0, 0, 0));
        messageService.updateMessage(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateMessageWithNullDateTimeTest() {
        Message message = new Message(1L, 2L, 3L, "NewMessage", null);
        messageService.updateMessage(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateMessageWithNullFromUserIdTest() {
        Message message = new Message(1L, null, 3L, "NewMessage", new DateTime(2014, 11, 22, 12, 0, 0, 0));
        messageService.updateMessage(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateMessageWithNullToUserIdTest() {
        Message message = new Message(1L, 2L, null, "NewMessage", new DateTime(2014, 11, 22, 12, 0, 0, 0));
        messageService.updateMessage(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateMessageWithNullTextTest() {
        Message message = new Message(1L, 2L, 3L, null, new DateTime(2014, 11, 22, 12, 0, 0, 0));
        messageService.updateMessage(message);
    }

    @Test
    public void getAllMessagesTest() {
        List<Message> messages=new ArrayList<Message>();
        messages.add(new Message());
        messageDao.getAllMessages();
        expectLastCall().andReturn(messages);

        replay(messageDao);
        messageService.getAllMessages();
        verify(messageDao);

    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllMessagesWithoutMessagesTest() {
        List<Message> messages=new ArrayList<Message>();
        messageDao.getAllMessages();
        expectLastCall().andReturn(messages);

        replay(messageDao);
        messageService.getAllMessages();
        verify(messageDao);

    }

    @Test
    public void getAllMessagesByTimePeriodTest() {
        DateTime startTime=new DateTime(new DateTime(2014, 11, 22, 12, 0, 0, 0));
        DateTime finishTime=new DateTime(new DateTime(2014, 11, 23, 12, 0, 0, 0));

        List<Message> messages=new ArrayList<Message>();
        messages.add(new Message());
        messageDao.getAllMessagesByTimePeriod(startTime,finishTime);
        expectLastCall().andReturn(messages);

        replay(messageDao);
        messageService.getAllMessagesByTimePeriod(startTime,finishTime);
        verify(messageDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllMessagesByTimePeriodWithoutStartDateTimeTest() {
        DateTime startTime = null;
        DateTime finishTime=new DateTime(new DateTime(2014, 11, 23, 12, 0, 0, 0));
        List<Message> messages=new ArrayList<Message>();
        messages.add(new Message());
        messageDao.getAllMessagesByTimePeriod(startTime,finishTime);
        expectLastCall().andReturn(messages);

        replay(messageDao);
        messageService.getAllMessagesByTimePeriod(startTime,finishTime);
        verify(messageDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllMessagesByTimePeriodWithoutFinishDateTimeTest() {
        DateTime startTime = new DateTime(2014, 11, 22, 12, 0, 0, 0);
        DateTime finishTime =null;
        List<Message> messages=new ArrayList<Message>();
        messages.add(new Message());
        messageDao.getAllMessagesByTimePeriod(startTime,finishTime);
        expectLastCall().andReturn(messages);

        replay(messageDao);
        messageService.getAllMessagesByTimePeriod(startTime,finishTime);
        verify(messageDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllMessagesByTimePeriodWithWrongDateTimesTest() {
        DateTime startTime =new DateTime(2014, 11, 23, 12, 0, 0, 0) ;
        DateTime finishTime =new DateTime(2014, 11, 22, 12, 0, 0, 0);
        List<Message> messages=new ArrayList<Message>();
        messages.add(new Message());
        messageDao.getAllMessagesByTimePeriod(startTime,finishTime);
        expectLastCall().andReturn(messages);

        replay(messageDao);
        messageService.getAllMessagesByTimePeriod(startTime,finishTime);
        verify(messageDao);
    }
}
