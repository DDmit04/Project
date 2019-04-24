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
public class ChatSessionService extends ChatSessionConnectionService {
	
	@Autowired
	private ChatSessionRepo chatSessionRepo;
	
	public void createNewChatSession(Chat chat, User currentUser) {
		ChatSession session = new ChatSession(chat, currentUser);
		session.setLastConnectionDate(LocalDateTime.now(Clock.systemUTC()));
		chatSessionRepo.save(session);
		ChatSessionConnection sessionConnection = openNewSessionConnection(session);
		session.setLastConnectionId(sessionConnection.getId());
		chatSessionRepo.save(session);
	}
	
	public void deleteChatSession(Chat chat, User currentUser) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, currentUser);
		chatSessionRepo.delete(session);
	}
	
	public void connectChatSesion(Chat chat, User user) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		ChatSessionConnection sessionConnection = openNewSessionConnection(session);
		session.setLastConnectionId(sessionConnection.getId());
		chatSessionRepo.save(session);
	}
	
	public void disconnectChatSession(Chat chat, User user) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		closeSessionConnection(session);
		chatSessionRepo.save(session);
	}
	
	public void updateLastConnectionDate(Chat chat, User user) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		session.setLastConnectionDate(LocalDateTime.now(Clock.systemUTC()));
		chatSessionRepo.save(session);
	}

	public ChatSession getSession(User currentUser, Chat chat) {
		return chatSessionRepo.findSessionByChatAndUser(chat, currentUser);
	}

	public Iterable<ChatSession> getUserChatSessions(User currentUser) {
		return chatSessionRepo.findSessionsByUser(currentUser);
	}

}
