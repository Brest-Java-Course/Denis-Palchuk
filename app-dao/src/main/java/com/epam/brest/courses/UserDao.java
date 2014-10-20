package com.epam.brest.courses;

import java.util.List;
import com.epam.brest.courses.domain.User;
/**
 * Created by denis on 10/20/14.
 */
public interface UserDao {
    public List<User> getUsers();
    public void removeUser(Long userId);
}
