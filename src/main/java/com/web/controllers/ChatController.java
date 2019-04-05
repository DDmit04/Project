package com.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.web.data.Chat;
import com.web.data.User;
import com.web.repository.ChatRepo;
import com.web.service.UserService;

@Controller
public class ChatController {
	
	@Autowired
	private ChatRepo chatRepo;
	
	@Autowired
	private UserService userService;

	@GetMapping("{user}/createChat")
	public String createChat(@AuthenticationPrincipal User currentUser, 
						     @PathVariable User user) {
		Chat chat = new Chat();
		chat.getChatMembers().add(currentUser);
		chat.getChatMembers().add(user);
		chatRepo.save(chat);
		return "redirect:/chats/" + chat.getId();
	}
}
