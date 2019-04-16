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
import lombok.Setter;

@Entity
@Setter
@Getter
public class ChatSessionConnection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime connectChat;
	private LocalDateTime disconnectChat;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chat_session_id")
	private ChatSession session;

}
