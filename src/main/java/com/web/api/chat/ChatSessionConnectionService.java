package com.web.api.chat;

import com.web.data.ChatSession;
import com.web.data.ChatSessionConnection;

public interface ChatSessionConnectionService {

	ChatSessionConnection openNewSessionConnection(ChatSession session);

	void closeSessionConnection(ChatSession session);
	
}
