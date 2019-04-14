package com.web.data.dto;

import com.web.data.FriendRequest;
import com.web.data.User;
import com.web.utils.DateUtil;

import lombok.Getter;

@Getter
public class FriendRequestDto {

	private Long id;
	private User requestFrom;
	private User requestTo;
	private Long requestFromId;
	private Long requestToId;
	private String friendRequestCreationDate;
	
	public FriendRequestDto(FriendRequest request) {
		this.id = request.getId();
		this.friendRequestCreationDate = DateUtil.formatDate(request.getFriendRequestCreationDate());
		this.requestFrom = request.getRequestFrom();
		this.requestTo = request.getRequestTo();
		this.requestToId = request.getRequestTo().getId();
		this.requestFromId = request.getRequestFrom().getId();
	}
}
