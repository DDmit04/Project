package com.web.exceptions;

import com.web.data.User;

public class UserPasswordException extends Exception{
	
	private User user;

	public UserPasswordException(String message, User user) {
		super(message);
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
}
