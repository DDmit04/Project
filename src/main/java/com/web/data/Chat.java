package com.web.data;

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
	
	 @ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(name = "user_id")
	 private User chatOwner;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) 
	@JoinTable(name = "user_chats", 
				joinColumns = { @JoinColumn(name = "chat_id") },
				inverseJoinColumns = { @JoinColumn(name = "user_id") } 
	)   	
	private Set<User> chatMembers = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) 
	@JoinTable(name = "saved_user_chats", 
				joinColumns = { @JoinColumn(name = "chat_id") },
				inverseJoinColumns = { @JoinColumn(name = "user_id") } 
	)   	
	private Set<User> chatsArcive = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   	@JoinTable(name = "chat_banned_users", 
   		joinColumns = { @JoinColumn(name = "group_id") },
   		inverseJoinColumns = { @JoinColumn(name = "user_id") } 
   	)
	private Set<User> chatBanList = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "admined_chats", 
		joinColumns = { @JoinColumn(name = "chat_id") },
		inverseJoinColumns = { @JoinColumn(name = "user_id") } 
	)
	private Set<User> chatAdmins = new HashSet<>();
	
	@OneToMany(mappedBy = "messageChat", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@javax.persistence.OrderBy("id")
	private Set<Message> chatMessages = new HashSet<>();
	
}
