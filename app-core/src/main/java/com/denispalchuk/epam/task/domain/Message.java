package com.denispalchuk.epam.task.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

/**
 * Created by denis on 11/15/14.
 */
public class Message {
    private Long messageId;
    private Long messageFromUserId;
    private Long messageToUserId;
    private String messageText;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDataDeserializer.class)
    private DateTime messageDateTime;

    public Message() {

    }
    public Message(Long messageId, Long messageFromUserId, Long messageToUserId, String messageText, DateTime messageDateTime) {
        this.messageId = messageId;
        this.messageFromUserId = messageFromUserId;
        this.messageToUserId = messageToUserId;
        this.messageText = messageText;
        this.messageDateTime = messageDateTime;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getMessageFromUserId() {
        return messageFromUserId;
    }

    public void setMessageFromUserId(Long messageFromUserId) {
        this.messageFromUserId = messageFromUserId;
    }

    public Long getMessageToUserId() {
        return messageToUserId;
    }

    public void setMessageToUserId(Long messageToUserId) {
        this.messageToUserId = messageToUserId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public DateTime getMessageDateTime() {
        return messageDateTime;
    }

    public void setMessageDateTime(DateTime messageDateTime) {
        this.messageDateTime = messageDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (messageDateTime != null ? !messageDateTime.equals(message.messageDateTime) : message.messageDateTime != null)
            return false;
        if (messageFromUserId != null ? !messageFromUserId.equals(message.messageFromUserId) : message.messageFromUserId != null)
            return false;
        if (messageId != null ? !messageId.equals(message.messageId) : message.messageId != null) return false;
        if (messageText != null ? !messageText.equals(message.messageText) : message.messageText != null) return false;
        if (messageToUserId != null ? !messageToUserId.equals(message.messageToUserId) : message.messageToUserId != null) return false;

        return true;
    }
    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", messageFromUserId=" + messageFromUserId +
                ", messageToUserId=" + messageToUserId +
                ", messageText='" + messageText + '\'' +
                ", messageDateTime=" + messageDateTime +
                '}';
    }
}