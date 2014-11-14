package com.epam.brest.courses.dao;

/**
 * Created by denis on 10/20/14.
 */

import com.epam.brest.courses.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UserDaoImpl implements UserDao {

    public static final String SELECT_ALL_USERS_SQL = "select userid, login, name from USER";
    public static final String SELECT_USER_BY_ID_SQL = "select userid, login, name from USER where userid = ?";
    public static final String SELECT_USER_BY_LOGIN_SQL = "select userid, login, name from USER where login = ?";
    public static final String DELETE_USER_BY_ID_SQL = "delete from USER where userid = ?";
    //@Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${insert_into_user_path}')).inputStream)}")
    public static final String addUserSql="insert into USER (userid, login,name) values (:userId, :login, :userName)";
    public static final String USER_ID = "userid";
    public static final String NAME = "name";
    public static final String LOGIN = "login";
    public static final String UPDATE_USER_SQL = "update user set name = ?, login = ? where userid = ?";
    public static final String LOG_GET_USER_BY_ID_USER_ID = "getUserById(UserId={})";
    public static final String LOG_GET_USER_BY_LOGIN_USER_LOGIN = "getUserByLogin(UserLogin={})";
    public static final String LOG_REMOVE_USER_BY_USER_ID = "removeUser(UserId={})";
    public static final String LOG_ADD_USER = "addUser({})";
    public static final String LOG_UPDATE_USER = "updateUser({})";
    public static final String LOG_GET_USERS = "getUsers()";


    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private KeyHolder keyHolder = new GeneratedKeyHolder();
    private static final Logger LOGGER = LogManager.getLogger();
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getUsers() {
        LOGGER.debug(LOG_GET_USERS);
        return jdbcTemplate.query(SELECT_ALL_USERS_SQL, new UserMapper());
    }

    @Override
    public User getUserById(Long id) {
        LOGGER.debug(LOG_GET_USER_BY_ID_USER_ID,id);
        return jdbcTemplate.queryForObject(SELECT_USER_BY_ID_SQL,
                new Object[]{id},new UserMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug(LOG_GET_USER_BY_LOGIN_USER_LOGIN,login);
        return jdbcTemplate.queryForObject(SELECT_USER_BY_LOGIN_SQL,
                new Object[]{login},new UserMapper());
    }
    @Override
    public void removeUser(Long userId) {
        LOGGER.debug(LOG_REMOVE_USER_BY_USER_ID,userId);
        jdbcTemplate.update(DELETE_USER_BY_ID_SQL,userId);
    }
    @Override
    public Long addUser(User user) {
        LOGGER.debug("addUser({}) ", user);
        Assert.notNull(user);
        Assert.notNull(user.getLogin(), "User login should be specified.");
        Assert.notNull(user.getUserName(), "User name should be specified.");
        SqlParameterSource parameterSource= new BeanPropertySqlParameterSource(user);
        namedJdbcTemplate.update(addUserSql, parameterSource,keyHolder);
        Long id=keyHolder.getKey().longValue();
        LOGGER.debug("Id User = {}",id);
        return id;
    }
    @Override
    public void updateUser(User user) {
        LOGGER.debug(LOG_UPDATE_USER,user);
        jdbcTemplate.update(UPDATE_USER_SQL,user.getUserName(),user.getLogin(),user.getUserId());
    }

    public class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUserId(resultSet.getLong(USER_ID));
            user.setUserName(resultSet.getString(NAME));
            user.setLogin(resultSet.getString(LOGIN));
            return user;
        }
    }
}
