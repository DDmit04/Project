package com.web.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.User;
import com.web.data.dto.ChatDto;
import com.web.repository.ChatRepo;
import com.web.repository.ChatSessionRepo;

@Service
public class ChatService {
	
	@Autowired
	private ChatRepo chatRepo;
	
	@Autowired
	private ChatSessionRepo chatSessionRepo;
	
	@Autowired
	private FileService fileService;
	
	public boolean userIsAdminOrOwner (User user, User currentUser, Chat chat) {
		return chat.getChatOwner().equals(currentUser) || chat.getChatAdmins().contains(currentUser);
	}
	
	public Chat createChat(String chatName, String chatTitle, MultipartFile file, User currentUser) throws IllegalStateException, IOException {
		Chat chat = new Chat(chatName, LocalDateTime.now());
		chat.setChatTitle(chatTitle);
		chat.setChatPicName(fileService.uploadFile(file, UploadType.CHAT_PIC));
		chat.setLastMessageDate(LocalDateTime.now());
		chatRepo.save(chat);
		chat.setChatOwner(currentUser);
		chat.getChatMembers().add(currentUser);
		chat.getChatAdmins().add(currentUser);
		chat.getChatsArcive().add(currentUser);
		chatRepo.save(chat);
		createSession(chat, currentUser);
		return chat;
	}

	private void createSession(Chat chat, User currentUser) {
		ChatSession session = new ChatSession(chat, currentUser, LocalDateTime.now());
		session.setLastView(LocalDateTime.now());
		chatSessionRepo.save(session);
	}
	
	private void deleteSession(Chat chat, User currentUser) {
		ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, currentUser);
		chatSessionRepo.delete(session);
	}

	public Chat createChat(User user, User currentUser) {
		Chat chat = new Chat();
		chat.setChatCreationDate(LocalDateTime.now());
		chat.setChatName(currentUser.getUsername() + " - " + user.getUsername());
		chat.setLastMessageDate(LocalDateTime.now());
		chatRepo.save(chat);
		chat.setChatOwner(currentUser);
		chat.getChatMembers().add(currentUser);
		chat.getChatMembers().add(user);
		chat.getChatsArcive().add(currentUser);
		chat.getChatsArcive().add(user);
		chatRepo.save(chat);
		createSession(chat, currentUser);
		createSession(chat, user);
		return chat;
	}
	
	public void geleteChatHitory(User user, User currentUser, Chat chat) {
		if(currentUser.equals(user)) {
			 if(chat.getChatAdmins().contains(user)) {
				 chat.getChatAdmins().remove(user);
			 }
			 if(chat.getChatMembers().contains(user)) {
				 chat.getChatMembers().remove(user);
			 }
			 chat.getChatsArcive().remove(user);
			 deleteSession(chat, currentUser);
			 chatRepo.save(chat);
		 }		
	}

	public void invateUser(User user, Chat chat) {
		chat.getChatMembers().add(user);
		chat.getChatsArcive().add(user);
		chatRepo.save(chat);		
		createSession(chat, user);
	}

	public void userLeave(User user, User currentUser, Chat chat) {
		if (currentUser.equals(user)) {
			chat.getChatMembers().remove(user);
			chatRepo.save(chat);
			ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
			session.setDisconnectChat(LocalDateTime.now());
			chatSessionRepo.save(session);
		}
	}

	public void userReturn(User user, Chat chat) {
		 if(user.getSavedChats().contains(chat)) {
			 chat.getChatMembers().add(user);
		 }
		 chatRepo.save(chat);
		 ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
		 session.setConnectChat(LocalDateTime.now());
		 chatSessionRepo.save(session);
	}

	public void chaseOutUser(User user, User currentUser, Chat chat) {
		if (userIsAdminOrOwner(user, currentUser, chat)) {
			chat.getChatMembers().remove(user);
			chatRepo.save(chat);
			ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
			session.setDisconnectChat(LocalDateTime.now());
			chatSessionRepo.save(session);
		}		
	}

	public void giveAdmin(User user, User currentUser, Chat chat) {
		if(chat.getChatOwner().equals(currentUser)) {
			chat.getChatAdmins().add(user);
			chatRepo.save(chat);
		}		
	}

	public void removeAdmin(User user, User currentUser, Chat chat) {
		if(user.equals(currentUser) || chat.getChatOwner().equals(currentUser)) {
			chat.getChatAdmins().remove(user);
			chatRepo.save(chat);
		}		
	}

	public void banUser(User user, User currentUser, Chat chat) {
		if(userIsAdminOrOwner(user, currentUser, chat)) {
			chat.getChatBanList().add(user);
			chat.getChatMembers().remove(user);
			chatRepo.save(chat);
			ChatSession session = chatSessionRepo.findSessionByChatAndUser(chat, user);
			session.setDisconnectChat(LocalDateTime.now());
			chatSessionRepo.save(session);
		}
	}

	public void unbanUser(User user, User currentUser, Chat chat) {
		if(userIsAdminOrOwner(user, currentUser, chat)) {
			chat.getChatBanList().remove(user);
			chatRepo.save(chat);
		}		
	}

	public void changeChatOwner(User user, User currentUser, Chat chat, String username, String password) {
		//Password encoder!!!
		User chatOwner = chat.getChatOwner();
		if(chatOwner.equals(currentUser) 
				&& username.equals(chatOwner.getUsername()) 
				&& password.equals(chatOwner.getPassword())) {
			chat.setChatOwner(user);
			chatRepo.save(chat);
		}		
	}
	
	public Iterable<ChatDto> findUserChats(User currentUser) {
		return chatRepo.findUserChats(currentUser);
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
}