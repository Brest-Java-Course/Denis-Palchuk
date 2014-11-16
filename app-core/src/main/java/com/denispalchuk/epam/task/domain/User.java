package com.denispalchuk.epam.task.domain;

import java.util.List;

/**
 * Created by denis on 11/15/14.
 */
public class User {
    private Long userId;
    private String userLogin;
    private String userName;
    private Integer userAge;

    public User(){
    }

    public User(Long userId, String userLogin, String userName, Integer userAge) {
        this.userId = userId;
        this.userLogin = userLogin;
        this.userName = userName;
        this.userAge = userAge;
    }

    public Long getUserId() {

        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userAge != null ? !userAge.equals(user.userAge) : user.userAge != null) return false;
        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (userLogin != null ? !userLogin.equals(user.userLogin) : user.userLogin != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userLogin='" + userLogin + '\'' +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                '}';
    }
}
