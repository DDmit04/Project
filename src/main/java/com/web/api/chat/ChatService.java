package com.web.api.chat;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.web.data.Chat;
import com.web.data.User;
import com.web.data.dto.ChatDto;

public interface ChatService {

	Chat createChat(User user, User currentUser);
	
	Chat createChat(Chat chat, User currentUser, MultipartFile file) throws IllegalStateException, IOException;
	
	void deleteChat(Chat chat);
	
	
	
	void changeChatOwner(User user, User currentUser, Chat chat, String username, String password);
	
	void updateChatSettings(User user, Chat chat, String newChatName, String chatTitle, MultipartFile file) throws IllegalStateException, IOException;
	
	Iterable<ChatDto> findUserChats(User user);

}
