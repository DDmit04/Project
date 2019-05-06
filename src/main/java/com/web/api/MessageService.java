package com.web.api;

import java.util.LinkedList;

import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.Message;
import com.web.data.MessageJson;

public interface MessageService {
	
	Message createMessage(Long chatId, MessageJson jsonMessage);
	
	
	

	LinkedList<Message> getChatMessages(ChatSession session, Chat chat);
	
}
