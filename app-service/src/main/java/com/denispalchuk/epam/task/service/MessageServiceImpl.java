package com.denispalchuk.epam.task.service;

import com.denispalchuk.epam.task.dao.MessageDao;
import com.denispalchuk.epam.task.domain.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by denis on 11/24/14.
 */
public class MessageServiceImpl implements MessageService {


    private MessageDao messageDao;
    private UserService userService;
    private static final Logger LOGGER= LogManager.getLogger();

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao=messageDao;
    }

    @Override
    public Long addMessage(Message message) {
        Assert.isNull(message.getMessageId(),"message id should be null");

        Assert.isNull(message.getMessageDateTime(), "message data should be null");

        Assert.notNull(message.getMessageFromUserId(), "message fromUserId can't be null");

        Assert.notNull(message.getMessageToUserId(),"message toUserId can't be null");

        Assert.notNull(message.getMessageText(),"message text can't be null");
        return messageDao.addMessage(message);
    }

    @Override
    public Message getMessageById(Long messageId) {
        Assert.notNull(messageId);

        Message message = new Message();
        try {
            message=messageDao.getMessageById(messageId);
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.error("message with id {} doesn't exist",messageId);
            throw new IllegalArgumentException("message with this id doesn't exist");
        }
        return message;
    }

    @Override
    public void removeMessage(Long messageId) {
    Assert.notNull(messageId);
        Message message = new Message();
        try {
            message=messageDao.getMessageById(messageId);
        } catch (EmptyResultDataAccessException ex) {
            LOGGER.error("message with id {} doesn't exist",messageId);
            throw new IllegalArgumentException("message with this id doesn't exist");
        }
        messageDao.removeMessage(messageId);
    }

    @Override
    public void updateMessage(Message message) {
        Assert.notNull(message.getMessageId(),"message id can't be null");
        Assert.notNull(message.getMessageDateTime(),"message date and time can't be null");
        Assert.notNull(message.getMessageText(),"message text can't be null");
        Assert.notNull(message.getMessageFromUserId(),"from user's id can't be null");
        Assert.notNull(message.getMessageToUserId(),"to user's id can't be null");
        messageDao.updateMessage(message);
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages=messageDao.getAllMessages();
        Assert.notEmpty(messages,"Empty list of messages");
        return messages;
    }

    @Override
    public List<Message> getAllMessagesByTimePeriod(LocalDateTime startDateTime, LocalDateTime finishDateTime) {
        Assert.notNull(startDateTime,"start date can't be null");
        Assert.notNull(finishDateTime,"finish date can't be null");
        if (startDateTime.toDateTime().getMillis() > finishDateTime.toDateTime().getMillis()) {
            LOGGER.error("wrong input Date from {}, to {}",startDateTime,finishDateTime);
            throw new IllegalArgumentException("wrong input Date range");
        }
        return messageDao.getAllMessagesByTimePeriod(startDateTime,finishDateTime);
    }

}
