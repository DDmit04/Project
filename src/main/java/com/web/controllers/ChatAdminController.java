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
import com.web.service.ChatService;

@Controller
public class ChatAdminController {
	
	@Autowired
	private ChatService chatService;
	
	@GetMapping("chats/{chat}/{user}/chaseOut")
	public String chaseOutUser(@AuthenticationPrincipal User currentUser, 
							   @PathVariable Chat chat, 
							   @PathVariable User user, 
							   Model model) {
//		uses chatSessionConnectionService 
		chatService.chaseOutUser(user, currentUser, chat);
		return "redirect:/chats/" + chat.getId();
	}
	 
	@GetMapping("/chats/{chat}/{user}/giveAdmin")
	public String giveAdmin(@AuthenticationPrincipal User currentUser,
							@PathVariable Chat chat,
							@PathVariable User user) {
		chatService.giveAdmin(user, currentUser, chat);
		return "redirect:/chats/" + chat.getId();
	}
	 
	@GetMapping("/chats/{chat}/{user}/removeAdmin")
	public String removeAdmin(@AuthenticationPrincipal User currentUser,
							  @PathVariable Chat chat,
							  @PathVariable User user) {
		chatService.removeAdmin(user, currentUser, chat);
		return "redirect:/chats/" + chat.getId();
	}

	@GetMapping("/chats/{chat}/{user}/ban")
	public String banUserInChat(@AuthenticationPrincipal User currentUser, 
								@PathVariable Chat chat,
								@PathVariable User user) {
//		uses chatSessionConnectionService 
		chatService.banUser(user, currentUser, chat);
		return "redirect:/chats/" + chat.getId();
	}

	@GetMapping("/chats/{chat}/{user}/unban")
	public String unbanUserInChat(@AuthenticationPrincipal User currentUser,
					 	 		  @PathVariable Chat chat,
					 	 		  @PathVariable User user) {
	 	chatService.unbanUser(user, currentUser, chat);
		return "redirect:/chats/" + chat.getId();
	}
	
	@GetMapping("/chats/{chat}/{user}/makeOwner")
	public String makeChatOwnerAuth(Model model) {
		model.addAttribute("loginAttention", "confirm the action on the group with login and password");
		return "login";
	}
		
	@PostMapping("/chats/{chat}/{user}/makeOwner")
	public String makeChatOwner(@AuthenticationPrincipal User currentUser,
							 	@RequestParam String username,
							 	@RequestParam String password,
							 	@PathVariable Chat chat,
							 	@PathVariable User user) {
		chatService.changeChatOwner(user, currentUser, chat, username, password);
		return "redirect:/chats/" + chat.getId();
	}
	
	@GetMapping("/chats/{chat}/settings")
	public String chatSettings(@PathVariable Chat chat,
							   Model model) {
		model.addAttribute("chatName", chat.getChatName());
		model.addAttribute("chatTitle", chat.getChatTitle());
		model.addAttribute("chatPicName", chat.getChatPicName());
		model.addAttribute("chat", chat);
		return "chatSettings";
	}
	
	@PostMapping("/chats/{chat}/settings")
	public String chatSettings(@AuthenticationPrincipal User currentUser,
							   @PathVariable Chat chat,
							   @RequestParam String chatName,
							   @RequestParam(required = false) String chatTitle,
							   @RequestParam("file") MultipartFile file,
							   Model model) throws IllegalStateException, IOException {
		chatService.updateChatSettings(currentUser, chat, chatName, chatTitle, file);
		return "redirect:/chats/" + chat.getId() + "/settings";
	}
	
}
