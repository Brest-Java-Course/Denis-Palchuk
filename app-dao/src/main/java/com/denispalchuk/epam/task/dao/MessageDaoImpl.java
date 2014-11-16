package com.denispalchuk.epam.task.dao;

import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
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
        namedParameterJdbcTemplate.update("insert into MESSAGE (messageId, messageFromId, messageToId, messageText, messageDateTime) values (:messageId, :messageFromId, :messageToId, :messageText, :messageDateTime)",
                parameterSource,keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Message getMessageById(Long messageId) {
        LOGGER.debug("get Message by Id {}",messageId);
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("messageId", messageId);
        return namedParameterJdbcTemplate.queryForObject("select messageId, messageFromId, messageToId, messageText, messageDateTime from MESSAGE where messageId = :messageId",
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
        return namedParameterJdbcTemplate.query("select messageId, messageFromId, messageToId, messageText, messageDateTime from MESSAGE",new MessageMapper());
    }

    @Override
    public void updateMessage(Message message) {
        LOGGER.debug("update message {}",message);
        Map<String, Object> parameters = new HashMap(2);
        parameters.put("messageDateTime",new Timestamp(message.getMessageDateTime().getMillis()));
        parameters.put("messageId",message.getMessageId());
        parameters.put("messageFromId", message.getMessageFromId());
        parameters.put("messageToId",message.getMessageToId());
        parameters.put("messageText",message.getMessageText());
        namedParameterJdbcTemplate.update("update MESSAGE set messageFromId = :messageFromId, messageToid = :messageToId, messageText = :messageText, messageDateTime = :messageDateTime where messageId = :messageId",
                parameters);
    }

    @Override
    public List<Message> getAllMessagesByTimePeriod(DateTime startDateTime, DateTime finishDateTime) {
        LOGGER.debug("get all messages by {} to {}",startDateTime,finishDateTime);
        Map<String, Object> parameters = new HashMap(2);
        parameters.put("startDateTime", new Timestamp(startDateTime.getMillis()));
        parameters.put("finishDateTime", new Timestamp(finishDateTime.getMillis()));
        return namedParameterJdbcTemplate.query("select messageId, messageFromId, messageToId, messageText, messageDateTime from MESSAGE where messageDateTime between :startDateTime and :finishDateTime",
                parameters, new MessageMapper());
    }

    public class MessageMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet resultSet, int i) throws SQLException {
            Message message = new Message();
            message.setMessageId(resultSet.getLong("messageId"));
            message.setMessageDateTime(new DateTime(resultSet.getTimestamp("messageDateTime")));
            message.setMessageFromId((resultSet.getLong("messageFromId")));
            message.setMessageToId(resultSet.getLong("messageToId"));
            message.setMessageText(resultSet.getString("messageText"));
            return message;
        }
    }
}

