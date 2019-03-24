package com.web.data.dto;

import com.web.data.User;

public class UserDto {

	private Long id;
	private String username;
	private boolean active;
	private String registrationDate;
	private String userPicName;
	private Long friendCount;
	private Long subscriptionsCount;
	private Long subscribersCount;
	private Long groupSubscriptionsCount;
	private boolean isFriend;
	private boolean isRequested;
	private boolean isSub;
	private boolean isGroupSub;
	private boolean isGroupAdmin;

//	user to user
	public UserDto(User user, Long frendCount, Long subscribersCount, Long subscriptionsCount, 
				   boolean isFriend, boolean isRequested, boolean isSub, Long groupSubscriptionsCount) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.active = user.isActive();
		this.registrationDate = user.getRegistrationDate();
		this.userPicName = user.getUserPicName();
		this.friendCount = frendCount;
		this.isFriend = isFriend;
		this.isRequested = isRequested;
		this.isSub = isSub;
		this.subscriptionsCount = subscriptionsCount;
		this.subscribersCount = subscribersCount;
		this.groupSubscriptionsCount = groupSubscriptionsCount;
	}

//	user to group
	public UserDto(User user, boolean isGroupSub) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.active = user.isActive();
		this.userPicName = user.getUserPicName();
		this.isGroupSub = isGroupSub;
//		this.isGroupAdmin = isGroupAdmin;
	}

//	user to list
	public UserDto(User user, Long frendCount, Long subscriptionsCount, Long subscribersCount, Long groupSubscriptionsCount) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.active = user.isActive();
		this.registrationDate = user.getRegistrationDate();
		this.userPicName = user.getUserPicName();
		this.friendCount = frendCount;
		this.isFriend = false;
		this.isRequested = false;
		this.isSub = false;
		this.subscriptionsCount = subscriptionsCount;
		this.subscribersCount = subscribersCount;
		this.groupSubscriptionsCount = groupSubscriptionsCount;


	}
	public boolean getIsGroupSub() {
		return isGroupSub;
	}
	public Long getGroupSubscriptionsCount() {
		return groupSubscriptionsCount;
	}
	public Long getSubscriptionsCount() {
		return subscriptionsCount;
	}
	public Long getSubscribersCount() {
		return subscribersCount;
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
		return friendCount;
	}
	public boolean getIsFriend() {
		return isFriend;
	}
	public boolean getIsSub() {
		return isSub;
	}
}
