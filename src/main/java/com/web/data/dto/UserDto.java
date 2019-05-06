package com.web.data.dto;

import java.time.LocalDateTime;

import com.web.data.Image;
import com.web.data.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto extends SearchResultsGeneric{

	private Long id;
	private Long friendCount;
	private Long subscriptionsCount;
	private Long subscribersCount;
	private Long groupSubscriptionsCount;
	private Long blackListCount;
    private String username;
	private LocalDateTime registrationDate;
	private String userInformation;
	private String userStatus;
	private boolean isFriend;
	private boolean isBloking;
	private boolean isBloked;
	private boolean isRequested;
	private boolean isSub;
	private boolean isGroupSub;
	private boolean isGroupAdmin;
	private boolean isBannedInGroup;
	private boolean isChatMember;
	private Image userImage;

//	user to user
	public UserDto(User user, 
				   Long frendCount, Long subscribersCount, Long subscriptionsCount, Long groupSubscriptionsCount,
				   boolean isFriend, boolean isRequested, boolean isSub, boolean isBloking, boolean isBloked) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.registrationDate = user.getRegistrationDate(); 
		this.userInformation = user.getUserInformation();
		this.userStatus = user.getUserStatus();
		this.userImage = user.getUserImage();
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
		this.userImage = user.getUserImage();
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
		this.userImage = user.getUserImage();
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
		this.userImage = user.getUserImage();
		this.userInformation = user.getUserInformation();
		this.userStatus = user.getUserStatus();
		this.friendCount = frendCount;
		this.subscriptionsCount = subscriptionsCount;
		this.subscribersCount = subscribersCount;
		this.groupSubscriptionsCount = groupSubscriptionsCount;
	}
	
//	user to chat
	public UserDto(User user, Long frendCount, Long subscriptionsCount, Long subscribersCount, boolean isChatMember) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.userImage = user.getUserImage();
		this.isChatMember = isChatMember;
		this.friendCount = frendCount;
		this.subscriptionsCount = subscriptionsCount;
		this.subscribersCount = subscribersCount;
	}
	
}