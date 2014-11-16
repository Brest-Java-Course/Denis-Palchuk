package com.denispalchuk.epam.task.dao;

import com.denispalchuk.epam.task.domain.Message;
import com.denispalchuk.epam.task.domain.User;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;


/**
 * Created by denis on 11/15/14.
 */
public class UserDaoImpl implements UserDao{


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private KeyHolder keyHolder = new GeneratedKeyHolder();
    private static final Logger LOGGER = LogManager.getLogger();

    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
    }



    @Override
    public List<User> getAllUsers() {
        LOGGER.debug("Get All users()");
        return namedParameterJdbcTemplate.query("select userId, userLogin, userName, userAge from USER", new UserMapper());
    }

    @Override
    public User getUserById(Long userId) {
        LOGGER.debug("get User by id {}",userId);
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("userId", userId);
        return namedParameterJdbcTemplate.queryForObject("select userId, userLogin, userName, userAge from USER where userId = :userId",
            parameters,new UserMapper());
    }

    @Override
    public User getUserByLogin(String userLogin) {
        LOGGER.debug("get user by login {}",userLogin);
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("userLogin", userLogin);
        return namedParameterJdbcTemplate.queryForObject("select userId, userLogin, userName, userAge from USER where userLogin = :userLogin",
                parameters,new UserMapper());
    }



    public Long addUser(User user) {
        LOGGER.debug("add user {}",user);
        SqlParameterSource parameterSource= new BeanPropertySqlParameterSource(user);
        namedParameterJdbcTemplate.update("insert into USER (userId, userLogin, userName, userAge) values (:userId, :userLogin, :userName, :userAge)",
                parameterSource,keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void removeUser(Long userId) {
        LOGGER.debug("remove user {}",userId);
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("userId", userId);
        namedParameterJdbcTemplate.update("delete from USER where userId = :userId",parameters);
    }

    @Override
    public void updateUser(User user) {
        LOGGER.debug("update user {}",user);
        SqlParameterSource parameterSource= new BeanPropertySqlParameterSource(user);
        namedParameterJdbcTemplate.update("update USER set userLogin = :userLogin, userName = :userName, userAge = :userAge where userId = :userId",
                parameterSource);
    }

    @Override
    public Integer getAverageAgeUsersWhoMessagedWithUser(Long userId) {
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("userId", userId);
        List<Integer> usersAges=namedParameterJdbcTemplate.query("select userAge from USER where userId in (select distinct messageFromId from MESSAGE where messageToId = :userId)",parameters,new IntegerMapper());
        int sumAges=0;
        for(Integer userAge:usersAges) {
            sumAges+=userAge;
        }
        return (int)(((double)sumAges/usersAges.size())+0.5);
    }

    @Override
    public List<Message> getAllMessageFromUser(Long userId) {
        LOGGER.debug("get all messages by user id {}",userId);
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("userId", userId);
        return namedParameterJdbcTemplate.query("select messageId, messageFromId, messageToId, messageText, messageDateTime from MESSAGE where messageToid = :userId",
                parameters, new MessageMapper());
    }



    public class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUserId(resultSet.getLong("userId"));
            user.setUserName(resultSet.getString("userName"));
            user.setUserLogin(resultSet.getString("userLogin"));
            user.setUserAge(resultSet.getInt("userAge"));
            return user;
        }
    }
    public class IntegerMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt("userAge");
        }
    }

    public class MessageMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet resultSet, int i) throws SQLException {
            Message message = new Message();
            message.setMessageDateTime(new DateTime(resultSet.getDate("messageDateTime")));
            message.setMessageFromId((resultSet.getLong("messageFromId")));
            message.setMessageToId(resultSet.getLong("messageToId"));
            message.setMessageText(resultSet.getString("messageText"));
            return message;
        }
    }
}
