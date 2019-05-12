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

import com.web.api.GroupService;
import com.web.api.ImageService;
import com.web.api.chat.ChatService;
import com.web.api.chat.ChatSessionService;
import com.web.api.user.UserService;
import com.web.data.Chat;
import com.web.data.ChatSession;
import com.web.data.User;
import com.web.data.dto.GroupDto;
import com.web.data.dto.ImageDto;
import com.web.data.dto.UserDto;
import com.web.service.ChatServiceImpl;
import com.web.service.ChatSessionServiceImpl;
import com.web.service.GroupServiceImpl;
import com.web.service.ImageServiceImpl;
import com.web.service.UserServiceImpl;

@Controller
public class ChatAlbumController {
	
	private UserService userService;
	private ImageService imageService;
	private ChatSessionService chatSessionService;
	private ChatService chatService;
	private GroupService groupService;
	
	@Autowired
	public ChatAlbumController(UserServiceImpl userService, ImageServiceImpl imageService, ChatSessionServiceImpl chatSessionService,
			ChatServiceImpl chatService, GroupServiceImpl groupService) {
		this.userService = userService;
		this.imageService = imageService;
		this.chatSessionService = chatSessionService;
		this.chatService = chatService;
		this.groupService = groupService;
	}

	@GetMapping("chats/{chat}/album")
	public String getChatAlbum(@AuthenticationPrincipal User currentUser, 
							   @PathVariable Chat chat, 
							   Model model) {
    	ChatSession session = chatSessionService.getSession(currentUser, chat);
		if (session != null) {
			UserDto userDto = userService.getOneUserToChat(currentUser, chat);
			Iterable<ImageDto> chatImages = imageService.findByImgChat(currentUser, chat);
			Iterable<GroupDto> adminedGroups = groupService.getAdminedGroups(currentUser);
			model.addAttribute("images", chatImages);
			model.addAttribute("user", userDto);
			model.addAttribute("currentChat", chat);
			model.addAttribute("adminedGroups", adminedGroups);
			return "album";
		} else {
			return "noAccessChat";
		}
	}
	
	@PostMapping("chats/{chat}/album")
	public String uploadChatImageAlbum(@AuthenticationPrincipal User currentUser, 
									   @PathVariable Chat chat, 
									   @RequestParam("file") MultipartFile file,
									   Model model) throws IllegalStateException, IOException {
    	ChatSession session = chatSessionService.getSession(currentUser, chat);
		if (session != null && file != null && !file.isEmpty()) {
			chatService.uploadChatPic(chat, file);
			return "redirect:/chats/" + chat.getId() + "/album";
		} else {
			return "noAccessChat";
		}
	}
	
}
