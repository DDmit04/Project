package com.web.data.dto;

import com.web.data.Chat;

public class ChatDto {
	
	private Long id;
	private Long adminCount;
	private Long membersCount;
	private String chatName;
	private String chatTitle;
	private String chatPicName;
	
//	chat ti chat
	public ChatDto(Chat chat, Long membersCount, Long adminCount) {
		this.id = chat.getId();
		this.chatName = chat.getChatName();
		this.chatTitle = chat.getChatTitle();
		this.chatPicName = chat.getChatPicName();
		this.adminCount = adminCount;
		this.membersCount = membersCount;
	}
	
//	chat to list
	public ChatDto(Chat chat) {
		this.id = chat.getId();
		this.chatName = chat.getChatName();
		this.chatTitle = chat.getChatTitle();
		this.chatPicName = chat.getChatPicName();
	}
	
	public Long getAdminCount() {
		return adminCount;
	}
	public Long getMembersCount() {
		return membersCount;
	}
	public Long getId() {
		return id;
	}
	public String getChatName() {
		return chatName;
	}
	public String getChatTitle() {
		return chatTitle;
	}
	public String getChatPicName() {
		return chatPicName;
	}
	
	

}
