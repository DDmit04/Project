package com.web.data.dto;

import java.time.LocalDateTime;

import com.web.data.FriendRequest;
import com.web.data.User;

import lombok.Getter;

@Getter
public class FriendRequestDto {

	private Long id;
	private User requestFrom;
	private User requestTo;
	private Long requestFromId;
	private Long requestToId;
	private LocalDateTime friendRequestCreationDate;
	
	public FriendRequestDto(FriendRequest request) {
		this.id = request.getId();
		this.friendRequestCreationDate = request.getFriendRequestCreationDate();
		this.requestFrom = request.getRequestFrom();
		this.requestTo = request.getRequestTo();
		this.requestToId = request.getRequestTo().getId();
		this.requestFromId = request.getRequestFrom().getId();
	}
}
