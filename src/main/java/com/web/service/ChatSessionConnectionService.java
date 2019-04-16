package com.web.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.data.ChatSession;
import com.web.data.ChatSessionConnection;
import com.web.repository.ChatSessionConnectionRepo;
import com.web.repository.ChatSessionRepo;

@Service
public class ChatSessionConnectionService {
	
	@Autowired
	private ChatSessionConnectionRepo chatSessionConnectionRepo;
	
	@Autowired
	private ChatSessionRepo chatSessionRepo;

	public ChatSessionConnection openNewSessionConnection(ChatSession session) {
		ChatSessionConnection sessionConnection = new ChatSessionConnection();
		sessionConnection.setConnectChat(LocalDateTime.now());
		sessionConnection.setSession(session);
		chatSessionConnectionRepo.save(sessionConnection);
		return sessionConnection;
	}

	public void closeSessionConnection(ChatSession session) {
		ChatSessionConnection sessionConnection = session.getSessionConnectionDates().iterator().next();
		sessionConnection.setDisconnectChat(LocalDateTime.now());
		sessionConnection.setSession(session);
		chatSessionConnectionRepo.save(sessionConnection);		
	}

	public void setLastView(ChatSession session) {
		session.setLastView(LocalDateTime.now());
		chatSessionRepo.save(session);		
	}
}
