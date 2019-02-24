package com.web.exceptions;

import com.web.data.User;

public class UserException extends Exception {
	
	private User user;
	
	public UserException(String message, User usr) {
		super(message);
		user = usr;
	}

	public User getUser() {
		return user;
	}
}
