package com.web.data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String messageText;
	private String messageDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User messageAuthor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_id")
	private Chat messageChat;
	
	public Message() {
	}
	public Message(String messageText) {
		this.messageText = messageText;
	}	
	public Chat getMessageChat() {
		return messageChat;
	}
	public void setMessageChat(Chat messageChat) {
		this.messageChat = messageChat;
	}
	public User getMessageAuthor() {
		return messageAuthor;
	}
	public void setMessageAuthor(User messageAuthor) {
		this.messageAuthor = messageAuthor;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public String getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
}
