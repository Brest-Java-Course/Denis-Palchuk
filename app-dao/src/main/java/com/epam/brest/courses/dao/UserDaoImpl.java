package com.epam.brest.courses.dao;

/**
 * Created by denis on 10/20/14.
 */

import com.epam.brest.courses.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UserDaoImpl implements UserDao {


    public static final String SELECT_ALL_USERS_SQL = "select userid, login, name from USER";
    public static final String SELECT_USER_BY_ID_SQL = "select userid, login, name from USER where userid = ?";
    public static final String SELECT_USER_BY_LOGIN_SQL = "select userid, login, name from USER where login = ?";
    public static final String DELETE_USER_BY_ID_SQL = "delete from USER where userid = ?";
    public static final String ADD_USER_SQL = "insert into USER (userid,login,name) values (?, ?, ?)";
    public static final String USER_ID = "userid";
    public static final String NAME = "name";
    public static final String LOGIN = "login";
    public static final String UPDATE_USER_SQL = "update user set name = ?, login = ? where userid = ?";
    private JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger();
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getUsers() {
        LOGGER.debug("getUsers()");
        return jdbcTemplate.query(SELECT_ALL_USERS_SQL, new UserMapper());
    }

    @Override
    public User getUserById(Long id) {
        LOGGER.debug("getUserById(UserId={})",id);
        return jdbcTemplate.queryForObject(SELECT_USER_BY_ID_SQL,
                new Object[]{id},new UserMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin(UserLogin={})",login);
        return jdbcTemplate.queryForObject(SELECT_USER_BY_LOGIN_SQL,
                new Object[]{login},new UserMapper());
    }
    @Override
    public void removeUser(Long userId) {
        LOGGER.debug("removeUser(UserId={})",userId);
        jdbcTemplate.update(DELETE_USER_BY_ID_SQL,userId);
    }
    @Override
    public void addUser(User user) {
        LOGGER.debug("addUser({})",user);
        jdbcTemplate.update(ADD_USER_SQL,
                user.getUserId(), user.getLogin(), user.getUserName());
    }
    @Override
    public void updateUser(User user) {
        LOGGER.debug("updateUser({})",user);
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
