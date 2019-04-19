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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String chatName;
	private String chatTitle;
	private String chatPicName;
	private LocalDateTime chatCreationDate;
	private LocalDateTime lastMessageDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User chatOwner;
	
	@OneToMany(mappedBy = "connectedChat", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<ChatSession> sessions;
	
	@OrderBy("id")	
	@OneToMany(mappedBy = "messageChat", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Message> chatMessages = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER) 
	@JoinTable(name = "user_chats", 
				joinColumns = { @JoinColumn(name = "chat_id") },
				inverseJoinColumns = { @JoinColumn(name = "user_id") } 
	)   	
	private Set<User> chatMembers = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
   	@JoinTable(name = "chat_banned_users", 
   		joinColumns = { @JoinColumn(name = "group_id") },
   		inverseJoinColumns = { @JoinColumn(name = "user_id") } 
   	)
	private Set<User> chatBanList = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "admined_chats", 
		joinColumns = { @JoinColumn(name = "chat_id") },
		inverseJoinColumns = { @JoinColumn(name = "user_id") } 
	)
	private Set<User> chatAdmins = new HashSet<>();
	
	public Chat(String chatName, LocalDateTime chatCreationDate) {
		this.chatName = chatName;
		this.chatCreationDate = chatCreationDate;
	}
}
