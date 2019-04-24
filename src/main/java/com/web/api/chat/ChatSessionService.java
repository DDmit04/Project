package com.web.api.chat;

import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.User;

public interface ChatSessionService {
	
	void createNewChatSession(User user, Chat chat);
	
	void deleteChatSession(User user, Chat chat);
	
	void connectChatSesion(User user, Chat chat);
	
	void disconnectChatSession(User user, Chat chat);
	
	
	
	void updateLastConnectionDate(User user, Chat chat);
	
	ChatSession getSession(User user, Chat chat);

	Iterable<ChatSession> getUserChatSessions(User currentUser);
	
}
