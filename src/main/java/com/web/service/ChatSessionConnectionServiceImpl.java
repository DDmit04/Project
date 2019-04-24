package com.web.service;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.chat.ChatSessionConnectionService;
import com.web.data.ChatSession;
import com.web.data.ChatSessionConnection;
import com.web.repository.ChatSessionConnectionRepo;

@Service
public class ChatSessionConnectionServiceImpl implements ChatSessionConnectionService {
	
	@Autowired
	private ChatSessionConnectionRepo chatSessionConnectionRepo;
	
	@Override
	public ChatSessionConnection openNewSessionConnection(ChatSession session) {
		ChatSessionConnection sessionConnection = new ChatSessionConnection();
		sessionConnection.setConnectChatDate(LocalDateTime.now(Clock.systemUTC()));
		sessionConnection.setSession(session);
		chatSessionConnectionRepo.save(sessionConnection);
		return sessionConnection;
	}

	@Override
	public void closeSessionConnection(ChatSession session) {
		ChatSessionConnection sessionConnection = session.getSessionConnectionDates().iterator().next();
		sessionConnection.setDisconnectChatDate(LocalDateTime.now(Clock.systemUTC()));
		sessionConnection.setSession(session);
		chatSessionConnectionRepo.save(sessionConnection);		
	}

}
