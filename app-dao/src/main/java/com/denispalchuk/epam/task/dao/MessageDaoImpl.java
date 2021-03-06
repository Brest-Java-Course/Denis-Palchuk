package com.denispalchuk.epam.task.dao;

import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by denis on 11/15/14.
 */

public class MessageDaoImpl implements MessageDao {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger();
    KeyHolder keyHolder = new GeneratedKeyHolder();

    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public Long addMessage(Message message) {
        LOGGER.debug("add Message {}",message);
        SqlParameterSource parameterSource= new BeanPropertySqlParameterSource(message);
        namedParameterJdbcTemplate.update("insert into MESSAGE (messageId, messageFromUserId, messageToUserId, messageText, messageDateTime) values (:messageId, :messageFromUserId, :messageToUserId, :messageText, NOW())",
                parameterSource,keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Message getMessageById(Long messageId) {
        LOGGER.debug("get Message by Id {}",messageId);
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("messageId", messageId);
        return namedParameterJdbcTemplate.queryForObject("select messageId, messageFromUserId, messageToUserId, messageText, messageDateTime from MESSAGE where messageId = :messageId",
                parameters,new MessageMapper());
    }

    @Override
    public void removeMessage(Long messageId) {
        LOGGER.debug("remove Message by Id {}",messageId);
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("messageId", messageId);
        namedParameterJdbcTemplate.update("delete from MESSAGE where messageId = :messageId",parameters);
    }

    @Override
    public List<Message> getAllMessages() {
        LOGGER.debug("get all users()");
        return namedParameterJdbcTemplate.query("select messageId, messageFromUserId, messageToUserId, messageText, messageDateTime from MESSAGE",new MessageMapper());
    }

    @Override
    public void updateMessage(Message message) {
        LOGGER.debug("update message {}",message);
        Map<String, Object> parameters = new HashMap(2);
        parameters.put("messageDateTime",new Timestamp(message.getMessageDateTime().toDateTime().getMillis()));
        parameters.put("messageId",message.getMessageId());
        parameters.put("messageFromUserId", message.getMessageFromUserId());
        parameters.put("messageToUserId",message.getMessageToUserId());
        parameters.put("messageText",message.getMessageText());
        namedParameterJdbcTemplate.update("update MESSAGE set messageFromUserId = :messageFromUserId, messageToUserId = :messageToUserId, messageText = :messageText, messageDateTime = :messageDateTime where messageId = :messageId",
                parameters);
    }

    @Override
    public List<Message> getAllMessagesByTimePeriod(LocalDateTime startDateTime, LocalDateTime finishDateTime) {
        LOGGER.debug("get all messages by {} to {}",startDateTime,finishDateTime);
        Map<String, Object> parameters = new HashMap(2);
        parameters.put("startDateTime", new Timestamp(startDateTime.toDateTime().getMillis()));
        parameters.put("finishDateTime", new Timestamp(finishDateTime.toDateTime().getMillis()));
        return namedParameterJdbcTemplate.query("select messageId, messageFromUserId, messageToUserId, messageText, messageDateTime from MESSAGE where messageDateTime between :startDateTime and :finishDateTime",
                parameters, new MessageMapper());
    }

    public class MessageMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet resultSet, int i) throws SQLException {
            Message message = new Message();
            message.setMessageId(resultSet.getLong("messageId"));
            message.setMessageDateTime(new LocalDateTime(resultSet.getTimestamp("messageDateTime")));
            message.setMessageFromUserId((resultSet.getLong("messageFromUserId")));
            message.setMessageToUserId(resultSet.getLong("messageToUserId"));
            message.setMessageText(resultSet.getString("messageText"));
            return message;
        }
    }
}

