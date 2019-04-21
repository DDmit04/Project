package com.web.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.ChatSessionConnection;
import com.web.data.Message;
import com.web.data.MessageJson;
import com.web.data.User;
import com.web.data.dto.ChatDto;
import com.web.data.dto.UserDto;
import com.web.repository.ChatRepo;
import com.web.repository.MessageRepo;
import com.web.repository.UserRepo;
import com.web.utils.DateUtil;

@Service
public class MessageService {

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private ChatRepo chatRepo;

	@Autowired
	private UserRepo userRepo;

	public User findUserByUsername(User currentUser) {
		return userRepo.findByUsername(currentUser.getUsername());
	}

	public UserDto findOneUserToChat(User currentUser, Chat chat) {
		return userRepo.findOneUserToChat(currentUser.getId(), chat);
	}

	public ChatDto findOneChat(Chat chat) {
		return chatRepo.findOneChat(chat.getId());
	}

	public void createMessage(Long chatId, MessageJson jsonMessage) {
		LocalDateTime messageTime = jsonMessage.getMessageDate();
		Chat chat = chatRepo.findChatById(chatId);
		User messageAuthor = userRepo.findByUsername(jsonMessage.getSender());
		Message message = new Message(jsonMessage.getContent(), messageTime);
		message.setMessageAuthor(messageAuthor);
		message.setMessageChat(chat);
		messageRepo.save(message);

		chat.setLastMessageDate(messageTime);
		chatRepo.save(chat);
	}

	public LinkedList<Message> findChatMessages(ChatSession session, Chat chat) {
		Set<Message> messages = chat.getChatMessages();
		LinkedList<Message> chatMessages = new LinkedList<>();
		for(Message message : messages) {
			for(ChatSessionConnection connection : session.getSessionConnectionDates()) {
				LocalDateTime messageDate = message.getMessageDate();
				LocalDateTime connectionDate = connection.getConnectChat();
				LocalDateTime disconnectionDate = connection.getDisconnectChat();
				if(disconnectionDate == null && !DateUtil.isLater(connectionDate, messageDate) 
						|| (disconnectionDate != null && DateUtil.dateInRange(messageDate, connectionDate, disconnectionDate))) {
					chatMessages.add(message);
				}
			}
		}
		return chatMessages;
	}
}
