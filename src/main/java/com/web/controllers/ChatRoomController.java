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
public class ChatRoomController {
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Autowired
	private ChatSessionService chatSessionService;
	
	@GetMapping("/chats/{chat}/{user}/invate")
	public String invateUser(@AuthenticationPrincipal User currentUser,
						     @PathVariable User user,
						     @PathVariable Chat chat) {
		chatRoomService.invateUser(user, chat);
		chatSessionService.createNewChatSession(chat, user);
		return "redirect:/chats/" + chat.getId();
	}
	
	@GetMapping("chats/{chat}/{user}/leave")
	public String leaveChat(@AuthenticationPrincipal User currentUser, 
    						@PathVariable Chat chat, 
							@PathVariable User user, 
							Model model) {
		chatRoomService.userLeave(user, currentUser, chat);
		chatSessionService.disconnectChatSession(chat, user);
		return "redirect:/chats/" + chat.getId();
	}
	 
	@GetMapping("chats/{chat}/{user}/return")
	public String returnChat(@AuthenticationPrincipal User currentUser, 
							 @PathVariable Chat chat, 
							 @PathVariable User user, 
							 Model model) {
		chatRoomService.userReturn(user, chat);
		chatSessionService.connectChatSesion(chat, user);
		return "redirect:/chats/" + chat.getId();
	}
	 
	@GetMapping("chats/{chat}/{user}/delete")
	public String deleteChat(@AuthenticationPrincipal User currentUser, 
							 @PathVariable Chat chat, 
							 @PathVariable User user, 
							 Model model) {
		chatSessionService.deleteChatSession(chat, currentUser);
		chatRoomService.deleteChatHistory(user, currentUser, chat);
		return "redirect:/messages";
	}

}
