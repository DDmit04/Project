package com.web.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

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
    private Long lastConnectionId;
	private LocalDateTime lastView;
	
	@OrderBy("id desc")
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ChatSessionConnection> sessionConnectionDates = new HashSet<>();
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chat_id")
	private Chat connectedChat;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User connectedUser;
	
	public ChatSession(Chat chat, User user) {
		this.connectedChat = chat;
		this.connectedUser = user;
	}
	
}
