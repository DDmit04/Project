package com.web.service;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.ChatSessionConnection;
import com.web.data.User;
import com.web.repository.ChatSessionRepo;

@Service
public class ChatSessionService {
	
	@Autowired
	private ChatSessionRepo chatSessionRepo;
	
	@Autowired
	private ChatSessionConnectionService chatSessionConnectionService;
	
	public void createChatSession(Chat chat, User currentUser) {
		ChatSession session = new ChatSession(chat, currentUser);
		session.setLastView(LocalDateTime.now(Clock.systemUTC()));
		chatSessionRepo.save(session);
		ChatSessionConnection sessionConnection = chatSessionConnectionService.openNewSessionConnection(session);
		session.setLastConnectionId(sessionConnection.getId());
		chatSessionRepo.save(session);
	}
	
	public void deleteChatSession(Chat chat, User currentUser) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, currentUser);
		chatSessionRepo.delete(session);
	}
	
	public void setConnectionDate(Chat chat, User user) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		ChatSessionConnection sessionConnection = chatSessionConnectionService.openNewSessionConnection(session);
		session.setLastConnectionId(sessionConnection.getId());
		chatSessionRepo.save(session);
	}
	
	public void setLastView(Chat chat, User user) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		session.setLastView(LocalDateTime.now(Clock.systemUTC()));
		chatSessionRepo.save(session);
	}
	
	public void setDisonnectionDate(Chat chat, User user) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		chatSessionConnectionService.closeSessionConnection(session);
		chatSessionRepo.save(session);
	}

	public ChatSession findSession(User currentUser, Chat chat) {
		return chatSessionRepo.findSessionByChatAndUser(chat, currentUser);
	}

}
