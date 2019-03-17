package com.web.data.dto;

import com.web.data.User;
import com.web.data.UserGroup;

public class UserGroupDto {
	
	private Long id;
	private Long subCount;
	private String groupName;
	private String groupInformation;
	private String creationDate;
	private String groupPicName;
	private User groupOwner;
	private boolean isSub;
	
	public UserGroupDto(UserGroup userGroup, Long subCount, boolean isSub) {
		this.id = userGroup.getId();
		this.isSub = isSub;
		this.subCount = subCount;
		this.groupName = userGroup.getGroupName();
		this.groupInformation = userGroup.getGroupInformation();
		this.creationDate = userGroup.getCreationDate();
		this.groupPicName = userGroup.getGroupPicName();
		this.groupOwner = userGroup.getGroupOwner();
	}

	public Long getSubCount() {
		return subCount;
	}
	public boolean isSub() {
		return isSub;
	}
	public Long getId() {
		return id;
	}
	public String getGroupName() {
		return groupName;
	}
	public String getGroupInformation() {
		return groupInformation;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public String getGroupPicName() {
		return groupPicName;
	}
	public User getGroupOwner() {
		return groupOwner;
	}
	


}
