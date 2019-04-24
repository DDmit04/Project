package com.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.web.data.Chat;
import com.web.data.User;
import com.web.service.ChatRoomService;
import com.web.service.ChatSessionService;

@Controller
public class ChatAdminController {
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Autowired
	private ChatSessionService chatSessionService;
	
	@GetMapping("chats/{chat}/{user}/chaseOut")
	public String chaseOutUser(@AuthenticationPrincipal User currentUser, 
							   @PathVariable Chat chat, 
							   @PathVariable User user, 
							   Model model) {
		chatRoomService.chaseOutUser(user, currentUser, chat);
		chatSessionService.disconnectChatSession(chat, user);
		return "redirect:/chats/" + chat.getId();
	}
	 
	@GetMapping("/chats/{chat}/{user}/giveAdmin")
	public String giveAdmin(@AuthenticationPrincipal User currentUser,
							@PathVariable Chat chat,
							@PathVariable User user) {
		chatRoomService.giveAdmin(user, currentUser, chat);
		return "redirect:/chats/" + chat.getId();
	}
	 
	@GetMapping("/chats/{chat}/{user}/removeAdmin")
	public String removeAdmin(@AuthenticationPrincipal User currentUser,
							  @PathVariable Chat chat,
							  @PathVariable User user) {
		chatRoomService.removeAdmin(user, currentUser, chat);
		return "redirect:/chats/" + chat.getId();
	}

	@GetMapping("/chats/{chat}/{user}/ban")
	public String banUserInChat(@AuthenticationPrincipal User currentUser, 
								@PathVariable Chat chat,
								@PathVariable User user) {
		chatRoomService.banUser(user, currentUser, chat);
		chatSessionService.disconnectChatSession(chat, user);
		return "redirect:/chats/" + chat.getId();
	}

	@GetMapping("/chats/{chat}/{user}/unban")
	public String unbanUserInChat(@AuthenticationPrincipal User currentUser,
					 	 		  @PathVariable Chat chat,
					 	 		  @PathVariable User user) {
		chatRoomService.unbanUser(user, currentUser, chat);
		return "redirect:/chats/" + chat.getId();
	}
	
}
