package com.denispalchuk.epam.task.service;

import com.denispalchuk.epam.task.domain.Message;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by denis on 11/21/14.
 */
public interface MessageService {
    public Long addMessage(Message message);

    public Message getMessageById(Long messageId);

    public void removeMessage(Long messageId);

    public void updateMessage(Message message);

    public List<Message> getAllMessages();

    public List<Message> getAllMessagesByTimePeriod(DateTime startDateTime,DateTime finishDateTime);
}
