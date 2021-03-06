package com.web.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.MessageService;
import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.ChatSessionConnection;
import com.web.data.Message;
import com.web.data.MessageJson;
import com.web.data.User;
import com.web.repository.ChatRepo;
import com.web.repository.MessageRepo;
import com.web.repository.UserRepo;
import com.web.utils.DateUtil;

@Service
public class MessageServiceImpl implements MessageService {

	private MessageRepo messageRepo;
	private ChatRepo chatRepo;
	private UserRepo userRepo;
	
	@Autowired
	public MessageServiceImpl(MessageRepo messageRepo, ChatRepo chatRepo, UserRepo userRepo) {
		this.messageRepo = messageRepo;
		this.chatRepo = chatRepo;
		this.userRepo = userRepo;
	}

	@Override
	public Message createMessage(Long chatId, MessageJson jsonMessage) {
		LocalDateTime messageTime = jsonMessage.getMessageDate();
		Chat chat = chatRepo.findChatById(chatId);
		User messageAuthor = userRepo.findByUsernameOrEmail(jsonMessage.getSender());
		Message message = new Message(jsonMessage.getContent(), messageTime);
		message.setMessageAuthor(messageAuthor);
		message.setMessageChat(chat);
		message.setMessageDate(LocalDateTime.now(Clock.systemUTC()));
		messageRepo.save(message);

		chat.setLastMessageDate(messageTime);
		chatRepo.save(chat);
		return message;
	}

	@Override
	public LinkedList<Message> getChatMessages(ChatSession session, Chat chat) {
		Set<Message> messages = chat.getChatMessages();
		LinkedList<Message> chatMessages = new LinkedList<>();
		for(Message message : messages) {
			for(ChatSessionConnection connection : session.getSessionConnectionDates()) {
				LocalDateTime messageDate = message.getMessageDate();
				LocalDateTime connectionDate = connection.getConnectChatDate();
				LocalDateTime disconnectionDate = connection.getDisconnectChatDate();
				if(disconnectionDate == null && !DateUtil.isLater(connectionDate, messageDate) 
						|| (disconnectionDate != null && DateUtil.dateInRange(messageDate, connectionDate, disconnectionDate))) {
					chatMessages.add(message);
				}
			}
		}
		return chatMessages;
	}
}
