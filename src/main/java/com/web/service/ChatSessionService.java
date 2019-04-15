package com.web.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.ChatSessionConnection;
import com.web.data.User;
import com.web.repository.ChatSessionRepo;
import com.web.repository.ChatSessionConnectionRepo;

@Service
public class ChatSessionService {
	
	@Autowired
	private ChatSessionRepo chatSessionRepo;
	
	@Autowired
	private ChatSessionConnectionRepo connectionsRepo;

	public void createChatSession(Chat chat, User currentUser) {
		ChatSession session = new ChatSession(chat, currentUser, true);
		ChatSessionConnection a = new ChatSessionConnection();
		a.setConnectChat(LocalDateTime.now());
		a.setSession(session);
		a.setLastDate(true);
		session.setLastView(LocalDateTime.now());
		chatSessionRepo.save(session);
		connectionsRepo.save(a);
		session.setLastConnectionId(a.getId());
		chatSessionRepo.save(session);

	}
	
	public void deleteChatSession(Chat chat, User currentUser) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, currentUser);
		chatSessionRepo.delete(session);
	}
	
	public void setConnectionDate(Chat chat, User user) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		ChatSessionConnection a = new ChatSessionConnection();
		a.setConnectChat(LocalDateTime.now());
		a.setSession(session);
		a.setLastDate(true);
		session.setConnected(true);
		chatSessionRepo.save(session);
		connectionsRepo.save(a);
		session.setLastConnectionId(a.getId());
		chatSessionRepo.save(session);
	}
	
	public void setDisonnectionDate(Chat chat, User user) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		ChatSessionConnection a = connectionsRepo.findConnectionById(session.getLastConnectionId());
		a.setDisconnectChat(LocalDateTime.now());
		a.setSession(session);
		a.setLastDate(false);
		connectionsRepo.save(a);
		session.setConnected(false);
		chatSessionRepo.save(session);
	}
	
}
