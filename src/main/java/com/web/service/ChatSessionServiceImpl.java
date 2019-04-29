package com.web.service;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.chat.ChatSessionConnectionService;
import com.web.api.chat.ChatSessionService;
import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.ChatSessionConnection;
import com.web.data.User;
import com.web.repository.ChatSessionRepo;

@Service
public class ChatSessionServiceImpl implements ChatSessionService {
	
	private ChatSessionRepo chatSessionRepo;
	private ChatSessionConnectionService сhatSessionConnectionService;
	
	@Autowired
	public ChatSessionServiceImpl(ChatSessionRepo chatSessionRepo, ChatSessionConnectionServiceImpl сhatSessionConnectionService) {
		this.chatSessionRepo = chatSessionRepo;
		this.сhatSessionConnectionService = сhatSessionConnectionService;
	}

	@Override
	public ChatSession createNewChatSession(User user, Chat chat) {
		ChatSession session = new ChatSession(chat, user);
		session.setLastConnectionDate(LocalDateTime.now(Clock.systemUTC()));
		chatSessionRepo.save(session);
		ChatSessionConnection sessionConnection = сhatSessionConnectionService.openNewSessionConnection(session);
		session.setLastConnectionId(sessionConnection.getId());
		chatSessionRepo.save(session);
		return session;
	}
	
	@Override
	public void deleteChatSession(User user, Chat chat) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		chatSessionRepo.delete(session);
	}
	
	@Override
	public ChatSession connectChatSesion(User user, Chat chat) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		ChatSessionConnection sessionConnection = сhatSessionConnectionService.openNewSessionConnection(session);
		session.setLastConnectionId(sessionConnection.getId());
		chatSessionRepo.save(session);
		return session;
	}
	
	@Override
	public ChatSession disconnectChatSession(User user, Chat chat) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		сhatSessionConnectionService.closeSessionConnection(session);
		chatSessionRepo.save(session);
		return session;
	}
	
	@Override
	public ChatSession updateLastConnectionDate(User user, Chat chat) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		session.setLastConnectionDate(LocalDateTime.now(Clock.systemUTC()));
		chatSessionRepo.save(session);
		return session;
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
