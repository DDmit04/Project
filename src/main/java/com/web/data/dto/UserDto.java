package com.web.data.dto;

import com.web.data.User;

public class UserDto {
	
	private Long id;
	private String username;
	private boolean active;
	private String registrationDate;
	private String userPicName;
	private Long frindCount;
	private boolean isFriend;
	private boolean isRequested;
	
	public UserDto(User user, Long frendCount, boolean isFriend, boolean isRequested) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.active = user.isActive();
		this.registrationDate = user.getRegistrationDate();
		this.userPicName = user.getUserPicName();
		this.frindCount = frendCount;
		this.isFriend = isFriend;
		this.isRequested = isRequested;
	}
	public boolean getIsRequested() {
		return isRequested;
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
	public Long getFriendCount() {
		return frindCount;
	}
	public boolean getIsFriend() {
		return isFriend;
	}

	
}
