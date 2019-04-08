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
import javax.persistence.OneToMany;

@Entity
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String chatName;
	private String chatTitle;
	private String chatPicName;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) 
	@JoinTable(name = "user_chats", 
				joinColumns = { @JoinColumn(name = "chat_id") },
				inverseJoinColumns = { @JoinColumn(name = "user_id") } 
	)   	
	private Set<User> chatMembers = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "admined_chats", 
		joinColumns = { @JoinColumn(name = "chat_id") },
		inverseJoinColumns = { @JoinColumn(name = "user_id") } 
	)
	private Set<User> chatAdmins = new HashSet<>();
	
	@OneToMany(mappedBy = "messageChat", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@javax.persistence.OrderBy("id")
	private Set<Message> chatMessages = new HashSet<>();
	
	public Chat() {
	}
	public Set<User> getChatAdmins() {
		return chatAdmins;
	}
	public void setChatAdmins(Set<User> chatAdmins) {
		this.chatAdmins = chatAdmins;
	}

	public String getChatPicName() {
		return chatPicName;
	}
	public void setChatPicName(String chatPicName) {
		this.chatPicName = chatPicName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<User> getChatMembers() {
		return chatMembers;
	}
	public void setChatMembers(Set<User> chatMembers) {
		this.chatMembers = chatMembers;
	}
	public Set<Message> getChatMessages() {
		return chatMessages;
	}
	public void setChatMessages(Set<Message> chatMessages) {
		this.chatMessages = chatMessages;
	}
	public String getChatName() {
		return chatName;
	}
	public void setChatName(String chatName) {
		this.chatName = chatName;
	}
	public String getChatTitle() {
		return chatTitle;
	}
	public void setChatTitle(String chatTitle) {
		this.chatTitle = chatTitle;
	}
	
}
