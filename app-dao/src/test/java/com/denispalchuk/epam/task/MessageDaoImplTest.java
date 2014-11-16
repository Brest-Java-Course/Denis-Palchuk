package com.denispalchuk.epam.task;

import com.denispalchuk.epam.task.dao.MessageDao;
import com.denispalchuk.epam.task.domain.Message;
import org.joda.time.DateTime;
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
public class MessageDaoImplTest {

    @Autowired
    private MessageDao messageDao;

    @Test
    public void getAllMessagesTest() {
        List<Message> messages=messageDao.getAllMessages();
        assertEquals(3L,messages.size());
    }

    @Test
    public void addMessageTest() {
        Message message=new Message(new Long(4L),new Long(2L),new Long(3L),"testText",null);
        messageDao.addMessage(message);
        List<Message> messages=messageDao.getAllMessages();
        assertEquals(4,messages.size());
    }

    @Test
    public void getMessageByIdTest() {
        Message message=messageDao.getMessageById(1L);
        assertNotNull(message);
        assertEquals(new Long(1L),message.getMessageId());
    }

    @Test
    public void removeMessageTest() {
        messageDao.removeMessage(1L);
        List<Message> messages=messageDao.getAllMessages();
        assertEquals(2L,messages.size());
    }

    @Test
    public void updateMessageTest() {
        Message message=new Message(new Long(1L),new Long(1L),new Long(2L),"testText",new DateTime(2005, 3, 26, 12, 0, 0, 0));
        messageDao.updateMessage(message);
        Message newMessage=messageDao.getMessageById(1L);
        assertEquals(message,newMessage);
    }

    @Test
    public void getAllMessagesByTimePeriodTest() {
        List<Message> messages=messageDao.getAllMessagesByTimePeriod(new DateTime(2014, 11, 22, 12, 0, 0, 0),new DateTime(2014, 11, 24, 12, 0, 0, 0));
        assertEquals(3,messages.size());
    }
}
