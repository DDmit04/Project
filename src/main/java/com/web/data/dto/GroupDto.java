package com.web.data.dto;

import com.web.data.User;
import com.web.utils.DateUtil;

import lombok.Getter;

import com.web.data.Group;

@Getter
public class GroupDto {
	
	private Long id;
	private Long subCount;
	private Long adminCount;
	private Long groupBanCount;
	private String groupName;
	private String groupInformation;
	private String creationDate;
	private String groupPicName;
	private User groupOwner;
	private String groupTitle;
	
//	group to user
	public GroupDto(Group userGroup, Long subCount, Long adminCount, Long groupBanCount) {
		this.id = userGroup.getId();
		this.subCount = subCount;
		this.groupName = userGroup.getGroupName();
		this.groupInformation = userGroup.getGroupInformation();
		this.creationDate = DateUtil.formatDate(userGroup.getGroupCreationDate());
		this.groupPicName = userGroup.getGroupPicName();
		this.groupOwner = userGroup.getGroupOwner();
		this.groupTitle = userGroup.getGroupTitle();
		this.adminCount = adminCount;
		this.groupBanCount = groupBanCount;
	}
//	group to list
	public GroupDto(Group userGroup, Long subCount) {
		this.id = userGroup.getId();
		this.groupName = userGroup.getGroupName();
		this.groupInformation = userGroup.getGroupInformation();
		this.creationDate = DateUtil.formatDate(userGroup.getGroupCreationDate());
		this.groupPicName = userGroup.getGroupPicName();
		this.groupOwner = userGroup.getGroupOwner();
		this.groupTitle = userGroup.getGroupTitle();
		this.subCount = subCount;
	}
}
