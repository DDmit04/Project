package com.web.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.api.chat.ChatService;
import com.web.data.Chat;
import com.web.data.User;
import com.web.data.dto.ChatDto;
import com.web.repository.ChatRepo;

@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
	private ChatRepo chatRepo;
	
	@Autowired
	private FileServiceImpl fileService;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	public Chat createChat(Chat chat, MultipartFile file, User currentUser) throws IllegalStateException, IOException {
		chat.setChatCreationDate(LocalDateTime.now(Clock.systemUTC()));
		chat.setChatPicName(fileService.uploadFile(file, UploadType.CHAT_PIC));
		chat.setLastMessageDate(LocalDateTime.now(Clock.systemUTC()));
		chatRepo.save(chat);
		chat.setChatOwner(currentUser);
		chat.getChatMembers().add(currentUser);
		chat.getChatAdmins().add(currentUser);
		chatRepo.save(chat);
		return chat;
	}

	@Override
	public Chat createChat(User user, User currentUser) {
		Chat chat = chatRepo.findByChatName(currentUser.getUsername() + " - " + user.getUsername());
		if(chat == null) {
			chat = new Chat(currentUser.getUsername() + " - " + user.getUsername(), LocalDateTime.now());
			chat.setLastMessageDate(LocalDateTime.now(Clock.systemUTC()));
			chatRepo.save(chat);
			chat.setChatOwner(currentUser);
			chat.getChatMembers().add(currentUser);
			chat.getChatMembers().add(user);
			chat.getChatAdmins().add(currentUser);
			chat.getChatAdmins().add(user);
			chatRepo.save(chat);
		}
		return chat;
	}
	
	@Override
	public void deleteChat(Chat chat) {
		if (chat.getSessions().size() == 0) {
			chat.setChatOwner(null);
			chatRepo.delete(chat);
		}
	}

	public void changeChatOwner(User user, User currentUser, Chat chat, String username, String password) {
		//Password encoder!!!
		User chatOwner = chat.getChatOwner();
		if(chatOwner.equals(currentUser) 
				&& username.equals(chatOwner.getUsername()) 
				&& passwordEncoder.matches(password, chatOwner.getPassword())) {
			chat.setChatOwner(user);
			chatRepo.save(chat);
		}		
	}
	
	public void updateChatSettings(User currentUser, Chat chat, String newChatName, String chatTitle, MultipartFile file) 
			throws IllegalStateException, IOException {
		if(currentUser.equals(chat.getChatOwner()) || chat.getChatAdmins().contains(currentUser)) {
			chat.setChatName(newChatName);
			chat.setChatTitle(chatTitle);
			if(!file.isEmpty()) {
				chat.setChatPicName(fileService.uploadFile(file, UploadType.CHAT_PIC));
			}
			chatRepo.save(chat);
		}
	}
	
	public Iterable<ChatDto> findUserChats(User currentUser) {
		return chatRepo.findUserChats(currentUser);
	}
}