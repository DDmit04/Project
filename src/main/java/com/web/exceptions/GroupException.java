package com.web.exceptions;

import com.web.data.Group;

public class GroupException extends Exception {
	
	private Group group;

	public GroupException(String message, Group group) {
		super(message);
		this.group = group;	
	}
	
	public GroupException(String message) {
		super(message);
	}
	
	public Group getGroup() {
		return group;
	}
}