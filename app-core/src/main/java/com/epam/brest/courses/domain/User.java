package com.epam.brest.courses.domain;

public class User {

	private Long userId;
	
	private String login;
	
	private String userName;

    public User() {
    }
    public User(Long id,String login, String name) {
        this.userId=id;
        this.login=login;
        this.userName=name;
    }
    public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId=userId;
	}
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login=login;
    }
    public void setUserName(String userName) {
        this.userName=userName;
    }
    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
