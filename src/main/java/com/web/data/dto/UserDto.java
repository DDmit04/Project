package com.web.data.dto;

import com.web.data.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

	private Long id;
	private Long friendCount;
	private Long subscriptionsCount;
	private Long subscribersCount;
	private Long groupSubscriptionsCount;
	private Long blackListCount;
    private String username;
	private String registrationDate;
	private String userPicName;
	private boolean isFriend;
	private boolean isBloking;
	private boolean isBloked;
	private boolean isRequested;
	private boolean isSub;
	private boolean isGroupSub;
	private boolean isGroupAdmin;
	private boolean isBannedInGroup;
	private boolean isChatMember;

//	user to user
	public UserDto(User user, 
				   Long frendCount, Long subscribersCount, Long subscriptionsCount, Long groupSubscriptionsCount,
				   boolean isFriend, boolean isRequested, boolean isSub, boolean isBloking, boolean isBloked) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.registrationDate = user.getRegistrationDate();
		this.userPicName = user.getUserPicName();
		this.friendCount = frendCount;
		this.isFriend = isFriend;
		this.isRequested = isRequested;
		this.isSub = isSub;
		this.subscriptionsCount = subscriptionsCount;
		this.subscribersCount = subscribersCount;
		this.groupSubscriptionsCount = groupSubscriptionsCount;
		this.isBloking = isBloking;
		this.isBloked = isBloked;
	}

//	user to group
	public UserDto(User user, boolean isGroupSub, boolean isGroupAdmin, boolean isBannedInGroup) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.userPicName = user.getUserPicName();
		this.isGroupSub = isGroupSub;
		this.isGroupAdmin = isGroupAdmin;
		this.isBannedInGroup = isBannedInGroup;
	}

//	user to list
	public UserDto(User user, 
				   Long frendCount, Long subscriptionsCount, Long subscribersCount, 
				   Long groupSubscriptionsCount, Long blackListCount) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.registrationDate = user.getRegistrationDate();
		this.userPicName = user.getUserPicName();
		this.friendCount = frendCount;
		this.subscriptionsCount = subscriptionsCount;
		this.subscribersCount = subscribersCount;
		this.groupSubscriptionsCount = groupSubscriptionsCount;
		this.blackListCount = blackListCount;

	}
	
//	user to statistic
	public UserDto(User user, Long frendCount, Long subscribersCount, Long subscriptionsCount, Long groupSubscriptionsCount) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.registrationDate = user.getRegistrationDate();
		this.userPicName = user.getUserPicName();
		this.friendCount = frendCount;
		this.subscriptionsCount = subscriptionsCount;
		this.subscribersCount = subscribersCount;
		this.groupSubscriptionsCount = groupSubscriptionsCount;
	}
	
//	user to chat
	public UserDto(User user, Long frendCount, Long subscriptionsCount, Long subscribersCount, boolean isChatMember) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.registrationDate = user.getRegistrationDate();
		this.userPicName = user.getUserPicName();
		this.isChatMember = isChatMember;
		this.friendCount = frendCount;
		this.subscriptionsCount = subscriptionsCount;
		this.subscribersCount = subscribersCount;
	}
	
	public boolean getIsBloked() {
		return isBloked;
	}
	public boolean getIsBloking() {
		return isBloking;
	}
	public boolean getIsBannedInGroup() {
		return isBannedInGroup;
	}
	public boolean getIsGroupSub() {
		return isGroupSub;
	}
	public boolean getIsRequested() {
		return isRequested;
	}
	public boolean getIsFriend() {
		return isFriend;
	}
	public boolean getIsSub() {
		return isSub;
	}
	public boolean getIsGroupAdmin() {
		return isGroupAdmin;
	}
}