package com.epam.brest.courses.dao;

/**
 * Created by denis on 10/20/14.
 */

import com.epam.brest.courses.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query("select userid, login, name from USER", new UserMapper());
    }

    @Override
    public void removeUser(Long userId) {

    }

    public void addUser(User user) {
        jdbcTemplate.update("insert into USER (userid,login,name) values (?, ?, ?)",
                user.getUserId(), user.getLogin(), user.getUserName());
    }

    public class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUserId(resultSet.getLong("userid"));
            user.setUserName(resultSet.getString("name"));
            user.setLogin(resultSet.getString("login"));
            return user;
        }
    }
}
