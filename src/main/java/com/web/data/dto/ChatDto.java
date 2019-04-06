package com.web.data.dto;

import com.web.data.Chat;

public class ChatDto {
	
	private Long id;
	private String chatName;
	private String chatTitle;
	private String chatPicName;
	
	public ChatDto(Chat chat) {
		this.id = chat.getId();
		this.chatName = chat.getChatName();
		this.chatTitle = chat.getChatTitle();
		this.chatPicName = chat.getChatPicName();
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
