package com.web.data.dto;

import com.web.data.User;

public class UserDto {
	
	private Long id;
	private String username;
	private boolean active;
	private String registrationDate;
	private String userPicName;
	private Long frendCount;
	private boolean isFrend;
	
	public UserDto(User user, Long frendCount, boolean isFrend) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.active = user.isActive();
		this.registrationDate = user.getRegistrationDate();
		this.userPicName = user.getUserPicName();
		this.frendCount = frendCount;
		this.isFrend = isFrend;
	}
	public Long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public boolean getIsActive() {
		return active;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public String getUserPicName() {
		return userPicName;
	}
	public Long getFrendCount() {
		return frendCount;
	}
	public boolean getIsFrend() {
		return isFrend;
	}

	
}
