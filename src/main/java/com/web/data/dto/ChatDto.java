package com.web.data.dto;

import com.web.data.Chat;
import com.web.data.User;

import lombok.Getter;

@Getter
public class ChatDto {
	
	private Long id;
	private Long adminCount;
	private Long membersCount;
	private Long chatBanCount;
	private String chatName;
	private String chatTitle;
	private String chatPicName;
	private User chatOwner;
	
//	chat to chat
	public ChatDto(Chat chat, Long membersCount, Long adminCount, Long chatBanCount) {
		this.id = chat.getId();
		this.chatName = chat.getChatName();
		this.chatTitle = chat.getChatTitle();
		this.chatPicName = chat.getChatPicName();
		this.chatOwner = chat.getChatOwner();
		this.adminCount = adminCount;
		this.membersCount = membersCount;
		this.chatBanCount = chatBanCount;
	}
	
//	chat to list
	public ChatDto(Chat chat) {
		this.id = chat.getId();
		this.chatName = chat.getChatName();
		this.chatTitle = chat.getChatTitle();
		this.chatPicName = chat.getChatPicName();
	}
}
