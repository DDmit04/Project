package com.web.service;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.chat.ChatSessionService;
import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.ChatSessionConnection;
import com.web.data.User;
import com.web.repository.ChatSessionRepo;

@Service
public class ChatSessionServiceImpl implements ChatSessionService {
	
	@Autowired
	private ChatSessionRepo chatSessionRepo;
	
	@Autowired
	private ChatSessionConnectionServiceImpl сhatSessionConnectionService;
	
	@Override
	public void createNewChatSession(User user, Chat chat) {
		ChatSession session = new ChatSession(chat, user);
		session.setLastConnectionDate(LocalDateTime.now(Clock.systemUTC()));
		chatSessionRepo.save(session);
		ChatSessionConnection sessionConnection = сhatSessionConnectionService.openNewSessionConnection(session);
		session.setLastConnectionId(sessionConnection.getId());
		chatSessionRepo.save(session);
	}
	
	@Override
	public void deleteChatSession(User user, Chat chat) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		chatSessionRepo.delete(session);
	}
	
	@Override
	public void connectChatSesion(User user, Chat chat) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		ChatSessionConnection sessionConnection = сhatSessionConnectionService.openNewSessionConnection(session);
		session.setLastConnectionId(sessionConnection.getId());
		chatSessionRepo.save(session);
	}
	
	@Override
	public void disconnectChatSession(User user, Chat chat) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		сhatSessionConnectionService.closeSessionConnection(session);
		chatSessionRepo.save(session);
	}
	
	@Override
	public void updateLastConnectionDate(User user, Chat chat) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		session.setLastConnectionDate(LocalDateTime.now(Clock.systemUTC()));
		chatSessionRepo.save(session);
	}

	@Override
	public ChatSession getSession(User currentUser, Chat chat) {
		return chatSessionRepo.findSessionByChatAndUser(chat, currentUser);
	}

	@Override
	public Iterable<ChatSession> getUserChatSessions(User currentUser) {
		return chatSessionRepo.findSessionsByUser(currentUser);
	}

}
