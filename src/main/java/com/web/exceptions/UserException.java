package com.web.exceptions;

import com.web.data.User;

public class UserException extends Exception {
	
	private UserExceptionType userExceptionType;
	private User user;

	public UserException(String message, User user, UserExceptionType userExceptionType) {
		super(message);
		this.user = user;
		this.userExceptionType = userExceptionType;
	}
	
	public User getUser() {
		return user;
	}

	public UserExceptionType getUserExceptionType() {
		return userExceptionType;
	}
}
