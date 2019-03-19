package com.web.data.dto;

import com.web.data.User;
import com.web.data.UserGroup;

public class UserGroupDto {
	
	private Long id;
	private Long subCount;
	private Long adminCount;
	private String groupName;
	private String groupInformation;
	private String creationDate;
	private String groupPicName;
	private User groupOwner;
	private String groupTitle;
	
	public UserGroupDto(UserGroup userGroup, Long subCount, Long adminCount) {
		this.id = userGroup.getId();
		this.subCount = subCount;
		this.groupName = userGroup.getGroupName();
		this.groupInformation = userGroup.getGroupInformation();
		this.creationDate = userGroup.getCreationDate();
		this.groupPicName = userGroup.getGroupPicName();
		this.groupOwner = userGroup.getGroupOwner();
		this.groupTitle = userGroup.getGroupTitle();
		this.adminCount = adminCount;
	}

	public Long getAdminCount() {
		return adminCount;
	}
	public String getGroupTitle() {
		return groupTitle;
	}
	public Long getSubCount() {
		return subCount;
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
