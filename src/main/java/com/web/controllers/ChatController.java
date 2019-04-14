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
import com.web.utils.DateUtil;

@Controller
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ChatSessionRepo chatSessionRepo;
	
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
		return "redirect:/chats/" + chat.getId();
	}
	
	@GetMapping("{user}/createChat")
	public String createChat(@AuthenticationPrincipal User currentUser,
						     @PathVariable User user) {
		Chat chat = chatService.createChat(user, currentUser);
		return "redirect:/chats/" + chat.getId();
	}
	
	@GetMapping("/chats/{chat}/{user}/invate")
	public String invateUser(@AuthenticationPrincipal User currentUser,
						     @PathVariable User user,
						     @PathVariable Chat chat) {
		chatService.invateUser(user, chat);
		return "redirect:/chats/" + chat.getId();
	}
	
	@GetMapping("chats/{chat}/{user}/leave")
	public String leaveChat(@AuthenticationPrincipal User currentUser, 
    						@PathVariable Chat chat, 
							@PathVariable User user, 
							Model model) {
		chatService.userLeave(user, currentUser, chat);
		return "redirect:/chats/" + chat.getId();
	}
	 
	@GetMapping("chats/{chat}/{user}/return")
	public String returnChat(@AuthenticationPrincipal User currentUser, 
							 @PathVariable Chat chat, 
							 @PathVariable User user, 
							 Model model) {
		chatService.userReturn(user, chat);
		return "redirect:/chats/" + chat.getId();
	}
	 
	@GetMapping("chats/{chat}/{user}/delete")
	public String deleteChat(@AuthenticationPrincipal User currentUser, 
							 @PathVariable Chat chat, 
							 @PathVariable User user, 
							 Model model) {
		chatService.geleteChatHitory(user, currentUser, chat);
		return "redirect:/chats/" + chat.getId();
	}
	 
	@GetMapping("chats/{chat}/{user}/chaseOut")
	public String chaseOutUser(@AuthenticationPrincipal User currentUser, 
							   @PathVariable Chat chat, 
							   @PathVariable User user, 
							   Model model) {
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