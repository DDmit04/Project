package com.web.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "chats")
   	private Set<User> chatMembers = new HashSet<>();
	
	@OneToMany(mappedBy = "messageChat", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Message> chatMessages;
	
	public Chat() {
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
	
}
