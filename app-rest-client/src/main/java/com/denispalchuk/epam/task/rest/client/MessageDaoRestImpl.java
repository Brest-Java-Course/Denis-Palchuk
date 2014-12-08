package com.denispalchuk.epam.task.rest.client;

import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by denis on 12/8/14.
 */
public class MessageDaoRestImpl implements MessageDao{

    private static final Logger LOGGER = LogManager.getLogger();
    RestTemplate restTemplate = new RestTemplate();
    private static final String REST_URL="http://localhost:8080/messages/";
    @Override
    public Long addMessage(Message message) {
        Long id = restTemplate.postForObject(REST_URL, message, Long.class);
        LOGGER.debug("client request to add message {} and return id {}",message,id);
        return id;
    }

    @Override
    public Message getMessageById(Long messageId) {
        LOGGER.debug("client request message by id {}",messageId);
        Message message=restTemplate.getForObject(REST_URL+"id/{id}",Message.class,messageId);
        return message;
    }

    @Override
    public void removeMessage(Long messageId) {
        LOGGER.debug("client request to delete message with id {}",messageId);
        restTemplate.delete(REST_URL+"id/{messageId}",messageId);
    }

    @Override
    public void updateMessage(Message message) {
        LOGGER.debug("client request to update message with id {}",message.getMessageId());
        restTemplate.put(REST_URL,Message.class);
    }

    @Override
    public List<Message> getAllMessages() {
        LOGGER.debug("client request get all messages");
        List<Message> messages=restTemplate.getForObject(REST_URL,List.class);
        return messages;
    }

    @Override
    public List<Message> getAllMessagesByTimePeriod(LocalDateTime startDateTime, LocalDateTime finishDateTime) {
        LOGGER.debug("get all messages by time period from {} to {}",startDateTime,finishDateTime);
        Map<String,LocalDateTime> params= new HashMap<String, LocalDateTime>();
        params.put("startDateTime",startDateTime);
        params.put("finishDateTime",finishDateTime);
        return restTemplate.postForObject(REST_URL + "bytime",params, List.class);
    }
}
