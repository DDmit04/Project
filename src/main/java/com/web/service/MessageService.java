package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.data.Chat;
import com.web.data.Message;
import com.web.data.MessageJson;
import com.web.data.User;
import com.web.data.dto.ChatDto;
import com.web.data.dto.UserDto;
import com.web.repository.ChatRepo;
import com.web.repository.MessageRepo;
import com.web.repository.UserRepo;

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
			return userRepo.findOneToChat(currentUser.getId(), chat);
		}

		public ChatDto findOneChat(Chat chat) {
			return chatRepo.findOneChat(chat.getId());
		}

		public void createMessage(Long chatId, MessageJson jsonMessage) {
			Chat chat = chatRepo.findChatById(chatId);
	    	User messageAuthor = userRepo.findByUsername(jsonMessage.getSender());
	    	Message message = new Message(jsonMessage.getContent());
	    	message.setMessageAuthor(messageAuthor);
	    	message.setMessageChat(chat);
			messageRepo.save(message);			
		}
}
