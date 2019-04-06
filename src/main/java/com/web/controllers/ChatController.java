package com.web.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.Chat;
import com.web.data.User;
import com.web.repository.ChatRepo;
import com.web.service.FileService;
import com.web.service.UploadType;
import com.web.service.UserService;

@Controller
public class ChatController {
	
	@Autowired
	private ChatRepo chatRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("messages")
	public String getUserChats(@AuthenticationPrincipal User currentUser,
							   Model model) {
		model.addAttribute("userChats", chatRepo.findUserChats(currentUser));
		return "chatList";
	}
	
	@GetMapping(value = {"{user}/createChat", "/createChat"})
	public String createChat(@AuthenticationPrincipal User currentUser) {
		return "createChat";
	}
	
	@PostMapping("{user}/createChat")
	public String createChat(@AuthenticationPrincipal User currentUser,
							 @RequestParam String chatName,
							 @RequestParam String chatTitle,
							 @RequestParam("file") MultipartFile file,
						     @PathVariable User user) throws IllegalStateException, IOException {
		Chat chat = new Chat();
		chat.setChatName(chatName);
		chat.setChatTitle(chatTitle);
		chat.setChatPicName(fileService.uploadFile(file, UploadType.CHAT_PIC));
		chatRepo.save(chat);
		chat.getChatMembers().add(currentUser);
		chat.getChatMembers().add(user);
		chatRepo.save(chat);
		return "redirect:/chats/" + chat.getId();
	}
	
	@PostMapping("/createChat")
	public String createChat(@AuthenticationPrincipal User currentUser,
							 @RequestParam String chatName,
							 @RequestParam String chatTitle,
							 @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		Chat chat = new Chat();
		chat.setChatName(chatName);
		chat.setChatTitle(chatTitle);
		chat.setChatPicName(fileService.uploadFile(file, UploadType.CHAT_PIC));
		chatRepo.save(chat);
		chat.getChatMembers().add(currentUser);
		chatRepo.save(chat);
		return "redirect:/chats/" + chat.getId();
	}
}
