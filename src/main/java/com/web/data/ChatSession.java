package com.web.data;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatSession {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime lastView;
	private LocalDateTime connectChat;
	private LocalDateTime disconnectChat;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chat_id")
	private Chat connectedChat;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User connectedUser;
	
	public ChatSession(Chat chat, User user, LocalDateTime connectChat) {
		this.connectedChat = chat;
		this.connectedUser = user;
		this.connectChat = connectChat;
	}
	
}
