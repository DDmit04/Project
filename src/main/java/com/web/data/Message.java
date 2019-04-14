package com.web.data;

import java.time.LocalDate;
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
@Setter
@Getter
@NoArgsConstructor
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String messageText;
	private LocalDateTime messageDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User messageAuthor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_id")
	private Chat messageChat;
	
	public Message(String messageText, LocalDateTime messageDate) {
		this.messageText = messageText;
		this.messageDate = messageDate;
	}	
}
