package com.web.service;

import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.api.FileService;
import com.web.api.ImageService;
import com.web.api.chat.ChatService;
import com.web.api.chat.ChatSessionService;
import com.web.data.Chat;
import com.web.data.Image;
import com.web.data.User;
import com.web.data.dto.ChatDto;
import com.web.repository.ChatRepo;

@Service
public class ChatServiceImpl implements ChatService {
	
	@Value("${upload.path.chatImages}")
	private String chatImagesUploadPath;
	
	private ChatRepo chatRepo;
	private FileService fileService;
	private PasswordEncoder passwordEncoder; 
	private ChatSessionService chatSessionService;
	private ImageService imageService;
	
	@Autowired
	public ChatServiceImpl(ChatRepo chatRepo, PasswordEncoder passwordEncoder, 
						   FileServiceImpl fileService, ChatSessionServiceImpl chatSessionService, ImageServiceImpl imageService) {
		this.chatRepo = chatRepo;
		this.fileService = fileService;
		this.passwordEncoder = passwordEncoder;
		this.chatSessionService = chatSessionService;
		this.imageService = imageService;
	}

	public Chat createChat(Chat chat, User currentUser, MultipartFile file) throws IllegalStateException, IOException {
		chat.setChatCreationDate(LocalDateTime.now(Clock.systemUTC()));
		chat.setLastMessageDate(LocalDateTime.now(Clock.systemUTC()));
		chatRepo.save(chat);
		chat.setChatOwner(currentUser);
		chat.getChatMembers().add(currentUser);
		chat.getChatAdmins().add(currentUser);
		chatRepo.save(chat);
		if(!file.isEmpty()) {
			String filename = fileService.uploadFile(chat, file, UploadType.CHAT_PIC);
			Image image = imageService.createImage(chat, filename, file);
			chat.setChatImage(image);
			chatRepo.save(chat);
		}
		return chat;
	}

	@Override
	public Chat createChat(User user, User currentUser) {
		Chat chat = chatRepo.findByChatName(currentUser.getUsername() + "-" + user.getUsername());
		if(chat == null) {
			chat = new Chat(currentUser.getUsername() + "-" + user.getUsername(), LocalDateTime.now(Clock.systemUTC()));
			chat.setLastMessageDate(LocalDateTime.now(Clock.systemUTC()));
			chatRepo.save(chat);
			chat.setChatOwner(currentUser);
			chat.getChatMembers().add(currentUser);
			chat.getChatMembers().add(user);
			chat.getChatAdmins().add(currentUser);
			chat.getChatAdmins().add(user);
			chatRepo.save(chat);
			chatSessionService.createNewChatSession(currentUser, chat);
			chatSessionService.createNewChatSession(user, chat);
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
			chat.setChatTitle(chatTitle);
			updateChatPicsFolder(chat, newChatName);
			chat.setChatName(newChatName);
			chatRepo.save(chat);
			if(!file.isEmpty()) {
				String filename = fileService.uploadFile(chat, file, UploadType.CHAT_PIC);
				Image image = imageService.createImage(chat, filename, file);
				chat.setChatImage(image);
				chatRepo.save(chat);
			}
		}
	}

	private void updateChatPicsFolder(Chat chat, String newChatName) {
		File oldFolder = new File(chatImagesUploadPath + "/" + chat.getChatName());
		File newFolder = new File(chatImagesUploadPath + "/" + newChatName);
		if (!newFolder.exists()) {
			newFolder.mkdir();
		}
		for(File transportedFile : oldFolder.listFiles()) {
			transportedFile.renameTo(new File(chatImagesUploadPath + "/" + newChatName + "/" + transportedFile.getName()));
		}
		oldFolder.delete();
	}
	
	public Iterable<ChatDto> findUserChats(User currentUser) {
		return chatRepo.findUserChats(currentUser);
	}
	
	@Override
	public ChatDto getOneChat(Chat chat) {
		return chatRepo.findOneChat(chat.getId());
	}

}