package com.web.data.dto;

import java.time.LocalDateTime;

import com.web.data.Chat;
import com.web.data.Image;
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
	private LocalDateTime chatCreationDate;
	private User chatOwner;
	private Image chatImage;
	
//	chat to chat
	public ChatDto(Chat chat, Long membersCount, Long adminCount, Long chatBanCount) {
		this.id = chat.getId();
		this.chatName = chat.getChatName();
		this.chatTitle = chat.getChatTitle();
		this.chatOwner = chat.getChatOwner();
		this.chatCreationDate = chat.getChatCreationDate();
		this.chatImage = chat.getChatImage();
		this.adminCount = adminCount;
		this.membersCount = membersCount;
		this.chatBanCount = chatBanCount;
	}
	
//	chat to list
	public ChatDto(Chat chat) {
		this.id = chat.getId();
		this.chatName = chat.getChatName();
		this.chatTitle = chat.getChatTitle();
	}
}
