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
import com.web.data.ChatSession;
import com.web.data.User;
import com.web.repository.ChatSessionRepo;
import com.web.service.ChatService;
import com.web.service.ChatSessionService;
import com.web.utils.DateUtil;

@Controller
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ChatSessionRepo chatSessionRepo;
	
	@Autowired
	private ChatSessionService chatSessionService;
	
	@GetMapping("messages")
	public String getUserChats(@AuthenticationPrincipal User currentUser,
							   Model model) {
		Iterable<ChatSession> sessions = chatSessionRepo.findSessionsByUser(currentUser);
		model.addAttribute("chatSessions", sessions);
		model.addAttribute("DateUtills", new DateUtil());
		return "chatList";
	}
	
	@GetMapping("/createChat")
	public String createChat() {
		return "createChat";
	}
	
	@PostMapping("/createChat")
	public String createChat(@AuthenticationPrincipal User currentUser,
							 @RequestParam String chatName,
							 @RequestParam(required = false) String chatTitle,
							 @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		Chat chat = chatService.createChat(chatName, chatTitle, file, currentUser);
		chatSessionService.createChatSession(chat, currentUser);
		return "redirect:/chats/" + chat.getId();
	}
	
	@GetMapping("{user}/createChat")
	public String createChat(@AuthenticationPrincipal User currentUser,
						     @PathVariable User user) {
		Chat chat = chatService.createChat(user, currentUser);
		chatSessionService.createChatSession(chat, currentUser);
		chatSessionService.createChatSession(chat, user);
		return "redirect:/chats/" + chat.getId();
	}
	
	@GetMapping("/chats/{chat}/{user}/invate")
	public String invateUser(@AuthenticationPrincipal User currentUser,
						     @PathVariable User user,
						     @PathVariable Chat chat) {
		chatService.invateUser(user, chat);
		chatSessionService.createChatSession(chat, user);
		return "redirect:/chats/" + chat.getId();
	}
	
	@GetMapping("chats/{chat}/{user}/leave")
	public String leaveChat(@AuthenticationPrincipal User currentUser, 
    						@PathVariable Chat chat, 
							@PathVariable User user, 
							Model model) {
//		uses chatSessionConnectionService 
		chatService.userLeave(user, currentUser, chat);
		return "redirect:/chats/" + chat.getId();
	}
	 
	@GetMapping("chats/{chat}/{user}/return")
	public String returnChat(@AuthenticationPrincipal User currentUser, 
							 @PathVariable Chat chat, 
							 @PathVariable User user, 
							 Model model) {
//		uses chatSessionConnectionService 
		chatService.userReturn(user, chat);
		chatSessionService.setConnectionDate(chat, user);
		return "redirect:/chats/" + chat.getId();
	}
	 
	@GetMapping("chats/{chat}/{user}/delete")
	public String deleteChat(@AuthenticationPrincipal User currentUser, 
							 @PathVariable Chat chat, 
							 @PathVariable User user, 
							 Model model) {
		chatSessionService.deleteChatSession(chat, currentUser);
		chatService.geleteChatHitory(user, currentUser, chat);
		return "redirect:/messages";
	}
	 
}