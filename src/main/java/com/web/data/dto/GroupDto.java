package com.web.data.dto;

import java.time.LocalDateTime;

import com.web.data.Group;
import com.web.data.Image;
import com.web.data.User;

import lombok.Getter;

@Getter
public class GroupDto extends SearchResultsGeneric{
	
	private Long id;
	private Long subCount;
	private Long adminCount;
	private Long groupBanCount;
	private String groupName;
	private String groupInformation;
	private LocalDateTime creationDate;
	private User groupOwner;
	private String groupTitle;
	private Image groupImage;
	
//	group to user
	public GroupDto(Group userGroup, Long subCount, Long adminCount, Long groupBanCount) {
		this.id = userGroup.getId();
		this.subCount = subCount;
		this.groupName = userGroup.getGroupName();
		this.groupInformation = userGroup.getGroupInformation();
		this.creationDate = userGroup.getGroupCreationDate();
		this.groupOwner = userGroup.getGroupOwner();
		this.groupTitle = userGroup.getGroupTitle();
		this.groupImage = userGroup.getGroupImage();
		this.adminCount = adminCount;
		this.groupBanCount = groupBanCount;
	}
//	group to list
	public GroupDto(Group userGroup, Long subCount) {
		this.id = userGroup.getId();
		this.groupName = userGroup.getGroupName();
		this.groupInformation = userGroup.getGroupInformation();
		this.creationDate = userGroup.getGroupCreationDate();
		this.groupOwner = userGroup.getGroupOwner();
		this.groupTitle = userGroup.getGroupTitle();
		this.groupImage = userGroup.getGroupImage();
		this.subCount = subCount;
	}
	//group to user admined groups
	public GroupDto(Group userGroup) {
		this.id = userGroup.getId();
		this.groupName = userGroup.getGroupName();
	}
}
