package com.denispalchuk.epam.task.dao;

import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;
import org.joda.time.LocalDateTime;

import java.util.List;

/**
 * Created by denis on 11/15/14.
 */
public interface MessageDao {

    public Long addMessage(Message message);

    public Message getMessageById(Long messageId);

    public void removeMessage(Long messageId);

    public void updateMessage(Message message);

    public List<Message> getAllMessages();

    public List<Message> getAllMessagesByTimePeriod(LocalDateTime startDateTime,LocalDateTime finishDateTime);
}
