package com.web.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	private boolean isConnected;
	
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ChatSessionConnection> dates = new HashSet<>();
    
    private Long lastConnectionId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chat_id")
	private Chat connectedChat;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User connectedUser;
	
	public ChatSession(Chat chat, User user, boolean isConnected) {
		this.connectedChat = chat;
		this.connectedUser = user;
		this.isConnected = isConnected;
	}
	
}
