package com.denispalchuk.epam.task.domain;

import org.joda.time.DateTime;

/**
 * Created by denis on 11/15/14.
 */
public class Message {
    Long messageId;
    Long messageFromId;
    Long messageToId;
    String messageText;
    DateTime messageDateTime;

    public Message() {

    }
    public Message(Long messageId, Long messageFromId, Long messageToId, String messageText, DateTime messageDateTime) {
        this.messageId = messageId;
        this.messageFromId = messageFromId;
        this.messageToId = messageToId;
        this.messageText = messageText;
        this.messageDateTime = messageDateTime;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getMessageFromId() {
        return messageFromId;
    }

    public void setMessageFromId(Long messageFromId) {
        this.messageFromId = messageFromId;
    }

    public Long getMessageToId() {
        return messageToId;
    }

    public void setMessageToId(Long messageToId) {
        this.messageToId = messageToId;
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
        if (messageFromId != null ? !messageFromId.equals(message.messageFromId) : message.messageFromId != null)
            return false;
        if (messageId != null ? !messageId.equals(message.messageId) : message.messageId != null) return false;
        if (messageText != null ? !messageText.equals(message.messageText) : message.messageText != null) return false;
        if (messageToId != null ? !messageToId.equals(message.messageToId) : message.messageToId != null) return false;

        return true;
    }
    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", messageFromId=" + messageFromId +
                ", messageToId=" + messageToId +
                ", messageText='" + messageText + '\'' +
                ", messageDateTime=" + messageDateTime +
                '}';
    }
}